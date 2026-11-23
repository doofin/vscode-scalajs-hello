# Extension-host integration tests with `vscode-test`

`sbt test` runs the Scala.js MUnit tests under Node.js. It does not provide
VS Code's runtime `vscode` module, so tests that need editor APIs must run in a
VS Code Extension Development Host. The [`@vscode/test-cli`](https://github.com/microsoft/vscode-test-cli)
CLI launches that host and runs Mocha tests inside it.

This follows the [VS Code testing guide](https://code.visualstudio.com/api/working-with-extensions/testing-extension)
and the local [`helloworld-test-cli-sample`](./src-ref/vscode-extension-samples/helloworld-test-cli-sample/README.md).
The steps below describe how to add the test runner; they do not add its npm
dependencies or configure it in this repository.

## 1. Install the test runner

From the repository root, install the CLI and the VS Code desktop test runner:

```bash
npm install --save-dev @vscode/test-cli @vscode/test-electron
```

Add a script to the root `package.json`:

```json
{
  "scripts": {
    "test": "vscode-test"
  }
}
```

Keep any existing scripts when adding this entry.

## 2. Configure the CLI

Create `.vscode-test.mjs` in the repository root:

```js
import { defineConfig } from '@vscode/test-cli';

export default defineConfig({
  files: 'test/integration/**/*.test.js'
});
```

The CLI uses this file to find the test files and launch VS Code. Its default
VS Code version is Stable; set `version` or `workspaceFolder` in this
configuration when a test requires a specific version or workspace.

## 3. Add an extension-host test

Add JavaScript Mocha tests under `test/integration/`. They run directly, so no
additional TypeScript compiler setup is needed. For example, a test can
activate the extension and check that one of its commands is registered:

```js
const assert = require('node:assert/strict');
const vscode = require('vscode');

suite('Scala.js extension', () => {
  test('activates and registers Hello World', async () => {
    const extension = vscode.extensions.getExtension('test.vscode-scalajs-hello');
    assert.ok(extension);
    await extension.activate();

    const commands = await vscode.commands.getCommands(true);
    assert.ok(commands.includes('extension.helloWorld'));
  });
});
```

Tests can use VS Code APIs because they execute inside the launched extension
host, unlike `sbt test`. To test the language server, open a saved Plain Text
document in the test workspace and exercise the editor completion/diagnostic
APIs after activating the extension.

## 4. Build and run

Compile the extension and prepare its language-server files and dependencies
before starting the test host:

```bash
sbt fastOptJS prepareExtension
npm test
```

The CLI downloads the selected VS Code build on its first run. Run
`npx vscode-test --help` for options such as filtering configurations by label.
Do not run CLI integration tests while another instance of the same VS Code
build is running; close that instance first, or test with a different VS Code
build.

Use `sbt test` for pure Scala.js unit tests, and `npm test` for
tests that require VS Code APIs or the extension host.
