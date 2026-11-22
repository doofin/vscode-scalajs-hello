const {
  CompletionItemKind,
  DiagnosticSeverity,
  ProposedFeatures,
  TextDocuments,
  TextDocumentSyncKind,
  createConnection
} = require("vscode-languageserver/node");
const { TextDocument } = require("vscode-languageserver-textdocument");

const connection = createConnection(ProposedFeatures.all);
const documents = new TextDocuments(TextDocument);

connection.onInitialize(() => ({
  capabilities: {
    textDocumentSync: TextDocumentSyncKind.Incremental,
    completionProvider: { resolveProvider: true }
  }
}));

function publishDiagnostics(document) {
  const diagnostics = [];
  const pattern = /\b[A-Z]{2,}\b/g;
  const text = document.getText();
  let match;

  while ((match = pattern.exec(text)) !== null) {
    diagnostics.push({
      severity: DiagnosticSeverity.Warning,
      range: {
        start: document.positionAt(match.index),
        end: document.positionAt(match.index + match[0].length)
      },
      message: `${match[0]} is all uppercase.`,
      source: "lsp-sample"
    });
  }
  connection.sendDiagnostics({ uri: document.uri, diagnostics });
}

documents.onDidOpen(({ document }) => publishDiagnostics(document));
documents.onDidChangeContent(({ document }) => publishDiagnostics(document));
documents.onDidClose(({ document }) => {
  connection.sendDiagnostics({ uri: document.uri, diagnostics: [] });
});

connection.onCompletion(() => [
  { label: "TypeScript", kind: CompletionItemKind.Text, data: 1 },
  { label: "JavaScript", kind: CompletionItemKind.Text, data: 2 }
]);

connection.onCompletionResolve(item => {
  if (item.data === 1) {
    item.detail = "TypeScript details";
    item.documentation = "TypeScript documentation";
  } else if (item.data === 2) {
    item.detail = "JavaScript details";
    item.documentation = "JavaScript documentation";
  }
  return item;
});

documents.listen(connection);
connection.listen();
