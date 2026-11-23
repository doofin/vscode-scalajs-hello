# Scala.js extension-host test plan

## Goal

Run an integration test which can access VS Code APIs through Scala.js.

The integration test is written in Scala.js.

references:
- [`@vscode/test-cli`](https://github.com/microsoft/vscode-test-cli) as the mock tool.
- src-ref/vscode-extension-samples/helloworld-test-cli-sample/
- https://code.visualstudio.com/api/working-with-extensions/testing-extension

1. **Use `@vscode/test-cli` for desktop tests (recommended if the CLI is
   required):** add `@vscode/test-electron` too. This is the supported desktop
   setup in the [VS Code testing guide](https://code.visualstudio.com/api/working-with-extensions/testing-extension)
   and [`helloworld-test-cli-sample`](../src-ref/vscode-extension-samples/helloworld-test-cli-sample/).
2. **Keep only `@vscode/test-cli`:** this will not run desktop extension-host
   tests. Use a custom launcher around the installed `code` executable and
   VS Code's `--extensionDevelopmentPath` / `--extensionTestsPath` arguments
   instead; that is no longer the test CLI workflow.

The `@vscode/test-cli` dependency has already been added to `package.json`.
No test configuration or test implementation is part of this plan yet.

## Basic mechanism
Possible Scala.js approach: 
- compile a test entry point to out/test/*.test.js. Its Scala.js main registers Mocha suite/test callbacks and performs the VS Code API assertions. 
- vscode-test.mjs remains JavaScript configuration only; it points the CLI at the compiled Scala.js test file. 
- run with `npm test` 

## Planned Scala.js test flow

1. Add a Scala.js integration-test entry point under `src/test/scala`. Use
   generated VS Code facades for extension activation and command inspection.
   Keep MUnit tests for `sbt test`; the extension-host entry point should
   register its tests with the Mocha TDD globals (`suite` and `test`).
2. Compile the Scala.js entry point with `Test / fastOptJS` and emit its
   CommonJS JavaScript into `out/test/`, where it can resolve the extension's
   runtime packages from `out/node_modules`.
3. Ensure loading that compiled module registers the Mocha tests. Prefer a
   Scala.js main-module initializer; if the build cannot initialize it directly,
   use a minimal JavaScript loader that invokes an exported Scala.js registration
   function. The loader must contain no test assertions or VS Code test logic.
4. Add `.vscode-test.mjs` with `defineConfig` and set `files` to the compiled
   `out/test/**/*.test.js` from Scala.js code. Add an npm script for `vscode-test`.
5. run `npm test` 

## implementation results:

The extension-host smoke test is written in Scala.js. `@vscode/test-cli`
launches VS Code and Mocha loads the JavaScript generated from that Scala.js
test. The test itself uses Scala.js and the generated VS Code facades; the
JavaScript `.vscode-test.mjs` file only configures test discovery.

The desktop runner requires both `@vscode/test-cli` and
`@vscode/test-electron` as development dependencies.

## Test source and runner

- [`IntegrationTestMain.scala`](../src/inteTest/scala/vscextension/IntegrationTestMain.scala)
  registers a Mocha TDD suite and checks that the extension activates and
  registers `extension.helloWorld`.
- The `IntegrationTest` configuration compiles the Scala.js test with a main
  module initializer to `out/test/extension.test.js`.
- [`.vscode-test.mjs`](../.vscode-test.mjs) tells the CLI to load that compiled
  test module.
- MUnit tests remain separate and continue to use `sbt test`.

The integration test uses the `IntegrationTest` configuration rather than
`Test`: Scala.js reserves the `Test` module initializer for its MUnit runner.
The integration configuration disables that test initializer and enables the
main initializer so loading the generated file registers the Mocha suite.

## Run

From the project root:

```bash
npm test
```

The npm `pretest` script runs `sbt fastOptJS prepareExtension` and
`IntegrationTest / fastOptJS` before launching VS Code. On the first run,
`@vscode/test-electron` downloads VS Code Stable. The smoke test should report
one passing test.

To run the build steps separately:

```bash
sbt fastOptJS prepareExtension 'IntegrationTest / fastOptJS'
npx vscode-test
```

The CLI uses Mocha's TDD interface (`suite`/`test`). Keep integration assertions
in Scala.js; add only test-runner configuration or loading glue in JavaScript if
needed. Use MUnit for pure Scala.js unit tests, and the extension host for tests
that need VS Code APIs.
