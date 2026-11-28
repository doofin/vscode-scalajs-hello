package vscextension.facade

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import scala.annotation.nowarn

import typings.vscode.mod as vscode

/*   import typings.vscodeLanguageclient.mod.BaseLanguageClient
 */
@nowarn("msg=unused explicit parameter")
object vsLanguageClient {
  @js.native
  @JSImport("vscode-languageclient/node", "LanguageClient")
  class LanguageClient extends js.Object {
    def this(name: String, serverOptions: ServerOptions, clientOptions: LanguageClientOptions) =
      this()
    def this(
        name: String,
        serverOptions: ServerOptions,
        clientOptions: LanguageClientOptions,
        forceDebug: Boolean
    ) = this()
    def this(
        id: String,
        name: String,
        serverOptions: ServerOptions,
        clientOptions: LanguageClientOptions
    ) = this()
    def this(
        id: String,
        name: String,
        serverOptions: ServerOptions,
        clientOptions: LanguageClientOptions,
        forceDebug: Boolean
    ) = this()

    val name: String = js.native
    val middleware: js.Any = js.native
    val clientOptions: LanguageClientOptions = js.native
    val protocol2CodeConverter: js.Any = js.native
    val code2ProtocolConverter: js.Any = js.native
    val onTelemetry: vscode.Event[js.Any] = js.native
    val onDidChangeState: vscode.Event[StateChangeEvent] = js.native
    val outputChannel: vscode.OutputChannel = js.native
    val traceOutputChannel: vscode.OutputChannel = js.native
    val diagnostics: js.UndefOr[vscode.DiagnosticCollection] = js.native
    val state: Int = js.native
    val initializeResult: js.UndefOr[js.Any] = js.native

    def start(): js.Promise[Unit] = js.native
    def stop(): js.Promise[Unit] = js.native
    def stop(timeout: Double): js.Promise[Unit] = js.native
    def dispose(): js.Promise[Unit] = js.native
    def dispose(timeout: Double): js.Promise[Unit] = js.native
    def restart(): js.Promise[Unit] = js.native
    def isInDebugMode: Boolean = js.native
    def setTrace(value: Int): js.Promise[Unit] = js.native

    def sendRequest[R](method: String): js.Promise[R] = js.native
    def sendRequest[R](method: String, token: vscode.CancellationToken): js.Promise[R] = js.native
    def sendRequest[R](method: String, params: js.Any): js.Promise[R] = js.native
    def sendRequest[R](
        method: String,
        params: js.Any,
        token: vscode.CancellationToken
    ): js.Promise[R] = js.native
    def sendRequest[R](requestType: js.Object): js.Promise[R] = js.native
    def sendRequest[R](requestType: js.Object, params: js.Any): js.Promise[R] = js.native
    def sendRequest[R](
        requestType: js.Object,
        params: js.Any,
        token: vscode.CancellationToken
    ): js.Promise[R] = js.native
    def onRequest(
        method: String,
        handler: js.Function1[js.Any, js.Any]
    ): vscode.Disposable = js.native
    def onRequest(
        requestType: js.Object,
        handler: js.Function1[js.Any, js.Any]
    ): vscode.Disposable = js.native
    def sendNotification(method: String): js.Promise[Unit] = js.native
    def sendNotification(method: String, params: js.Any): js.Promise[Unit] = js.native
    def sendNotification(notificationType: js.Object): js.Promise[Unit] = js.native
    def sendNotification(notificationType: js.Object, params: js.Any): js.Promise[Unit] = js.native
    def onNotification(
        method: String,
        handler: js.Function1[js.Any, Unit]
    ): vscode.Disposable = js.native
    def onNotification(
        notificationType: js.Object,
        handler: js.Function1[js.Any, Unit]
    ): vscode.Disposable = js.native
    def sendProgress(
        progressType: js.Any,
        token: String | Double,
        value: js.Any
    ): js.Promise[Unit] = js.native
    def onProgress(
        progressType: js.Any,
        token: String | Double,
        handler: js.Function1[js.Any, Unit]
    ): vscode.Disposable = js.native

    def createDefaultErrorHandler(): ErrorHandler = js.native
    def createDefaultErrorHandler(maxRestartCount: Double): ErrorHandler = js.native
    def debug(message: String): Unit = js.native
    def debug(message: String, data: js.Any): Unit = js.native
    def debug(message: String, data: js.Any, showNotification: Boolean): Unit = js.native
    def info(message: String): Unit = js.native
    def info(message: String, data: js.Any): Unit = js.native
    def info(message: String, data: js.Any, showNotification: Boolean): Unit = js.native
    def warn(message: String): Unit = js.native
    def warn(message: String, data: js.Any): Unit = js.native
    def warn(message: String, data: js.Any, showNotification: Boolean): Unit = js.native
    def error(message: String): Unit = js.native
    def error(message: String, data: js.Any): Unit = js.native
    def error(message: String, data: js.Any, showNotification: js.Any): Unit = js.native

    def needsStart(): Boolean = js.native
    def needsStop(): Boolean = js.native
    def isRunning(): Boolean = js.native
    def registerFeatures(features: js.Array[js.Any]): Unit = js.native
    def registerFeature(feature: js.Any): Unit = js.native
    def registerProposedFeatures(): Unit = js.native
    def getFeature(request: String): js.Any = js.native
    def hasDedicatedTextSynchronizationFeature(document: vscode.TextDocument): Boolean = js.native
    def handleFailedRequest[T](
        request: js.Any,
        token: js.UndefOr[vscode.CancellationToken],
        error: js.Any,
        defaultValue: T,
        showNotification: js.UndefOr[Boolean] = js.undefined
    ): T = js.native
  }

  @js.native
  @JSImport("vscode-languageclient/node", "TransportKind")
  object TransportKind extends js.Object {
    val stdio: Int = js.native
    val ipc: Int = js.native
    val pipe: Int = js.native
    val socket: Int = js.native
  }

  @js.native
  @JSImport("vscode-languageclient/node", "Trace")
  object Trace extends js.Object {
    val Off: Int = js.native
    val Messages: Int = js.native
    val Verbose: Int = js.native
  }

  @js.native
  @JSImport("vscode-languageclient/node", "RevealOutputChannelOn")
  object RevealOutputChannelOn extends js.Object {
    val Debug: Int = js.native
    val Info: Int = js.native
    val Warn: Int = js.native
    val Error: Int = js.native
    val Never: Int = js.native
  }

  @js.native
  @JSImport("vscode-languageclient/node", "ErrorAction")
  object ErrorAction extends js.Object {
    val Continue: Int = js.native
    val Shutdown: Int = js.native
  }

  @js.native
  @JSImport("vscode-languageclient/node", "CloseAction")
  object CloseAction extends js.Object {
    val DoNotRestart: Int = js.native
    val Restart: Int = js.native
  }

  @js.native
  @JSImport("vscode-languageclient/node", "State")
  object State extends js.Object {
    val Stopped: Int = js.native
    val Starting: Int = js.native
    val Running: Int = js.native
  }

  @js.native
  trait StateChangeEvent extends js.Object {
    val oldState: Int = js.native
    val newState: Int = js.native
  }

  @js.native
  trait ErrorHandlerResult extends js.Object {
    val action: Int = js.native
    val message: js.UndefOr[String] = js.native
    val handled: js.UndefOr[Boolean] = js.native
  }

  @js.native
  trait CloseHandlerResult extends js.Object {
    val action: Int = js.native
    val message: js.UndefOr[String] = js.native
    val handled: js.UndefOr[Boolean] = js.native
  }

  @js.native
  trait ErrorHandler extends js.Object {
    def error(
        error: js.Any,
        message: js.UndefOr[js.Any],
        count: js.UndefOr[Double]
    ): js.Any = js.native
    def closed(): js.Any = js.native
  }

  @js.native
  trait SocketTransport extends js.Object {
    val kind: Int = js.native
    val port: Double = js.native
  }

  object SocketTransport {
    def apply(port: Double): SocketTransport =
      js.Dynamic.literal(kind = TransportKind.socket, port = port).asInstanceOf[SocketTransport]
  }

  @js.native
  trait ForkOptions extends js.Object {
    val cwd: js.UndefOr[String] = js.native
    val env: js.UndefOr[js.Any] = js.native
    val encoding: js.UndefOr[String] = js.native
    val execArgv: js.UndefOr[js.Array[String]] = js.native
  }

  @js.native
  trait NodeModule extends js.Object {
    val module: String = js.native
    val transport: js.UndefOr[Int | SocketTransport] = js.native
    val args: js.UndefOr[js.Array[String]] = js.native
    val runtime: js.UndefOr[String] = js.native
    val options: js.UndefOr[ForkOptions] = js.native
  }

  object NodeModule {
    def apply(module: String): NodeModule =
      js.Dynamic.literal(module = module).asInstanceOf[NodeModule]

    def apply(module: String, transport: Int): NodeModule =
      js.Dynamic.literal(module = module, transport = transport).asInstanceOf[NodeModule]

    def apply(
        module: String,
        transport: js.Any,
        args: js.Array[String],
        runtime: String,
        options: ForkOptions
    ): NodeModule =
      js.Dynamic
        .literal(
          module = module,
          transport = transport,
          args = args,
          runtime = runtime,
          options = options
        )
        .asInstanceOf[NodeModule]
  }

  @js.native
  trait ExecutableOptions extends js.Object {
    val cwd: js.UndefOr[String] = js.native
    val env: js.UndefOr[js.Any] = js.native
    val detached: js.UndefOr[Boolean] = js.native
    val shell: js.UndefOr[Boolean] = js.native
  }

  @js.native
  trait Executable extends js.Object {
    val command: String = js.native
    val transport: js.UndefOr[Int | SocketTransport] = js.native
    val args: js.UndefOr[js.Array[String]] = js.native
    val options: js.UndefOr[ExecutableOptions] = js.native
  }

  @js.native
  trait ServerOptionsPair extends js.Object {
    val run: js.Any = js.native
    val debug: js.Any = js.native
  }

  type ServerOptions =
    Executable | NodeModule | ServerOptionsPair | js.Function0[js.Promise[js.Any]]

  object ServerOptions {
    def apply(run: js.Any, debug: js.Any): ServerOptionsPair =
      js.Dynamic.literal(run = run, debug = debug).asInstanceOf[ServerOptionsPair]
  }

  object DocumentFilter {
    def apply(scheme: String, language: String): vscode.DocumentFilter =
      js.Dynamic.literal(scheme = scheme, language = language).asInstanceOf[vscode.DocumentFilter]
  }

  @js.native
  trait LanguageClientOptions extends js.Object {
    val documentSelector: js.UndefOr[js.Any] = js.native
    val diagnosticCollectionName: js.UndefOr[String] = js.native
    val outputChannel: js.UndefOr[vscode.OutputChannel] = js.native
    val outputChannelName: js.UndefOr[String] = js.native
    val traceOutputChannel: js.UndefOr[vscode.OutputChannel] = js.native
    val revealOutputChannelOn: js.UndefOr[Int] = js.native
    val stdioEncoding: js.UndefOr[String] = js.native
    val initializationOptions: js.UndefOr[js.Any] = js.native
    val initializationFailedHandler: js.UndefOr[js.Function1[js.Any, Boolean]] = js.native
    val progressOnInitialization: js.UndefOr[Boolean] = js.native
    val errorHandler: js.UndefOr[ErrorHandler] = js.native
    val middleware: js.UndefOr[js.Any] = js.native
    val uriConverters: js.UndefOr[js.Any] = js.native
    val workspaceFolder: js.UndefOr[vscode.WorkspaceFolder] = js.native
    val connectionOptions: js.UndefOr[js.Any] = js.native
    val markdown: js.UndefOr[js.Any] = js.native
    val notebookDocumentOptions: js.UndefOr[js.Any] = js.native
    val diagnosticPullOptions: js.UndefOr[js.Any] = js.native
    val synchronize: js.UndefOr[js.Any] = js.native
  }

  object LanguageClientOptions {
    def apply(
        documentSelector: js.Array[vscode.DocumentFilter],
        outputChannel: vscode.OutputChannel,
        traceOutputChannel: js.UndefOr[vscode.OutputChannel] = js.undefined,
        initializationOptions: js.UndefOr[js.Any] = js.undefined
    ): LanguageClientOptions =
      js.Dynamic
        .literal(
          documentSelector = documentSelector,
          outputChannel = outputChannel,
          traceOutputChannel = traceOutputChannel,
          initializationOptions = initializationOptions
        )
        .asInstanceOf[LanguageClientOptions]
  }

}
