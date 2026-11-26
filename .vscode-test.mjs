import { defineConfig } from '@vscode/test-cli';

export default defineConfig({
  files: 'out/test/**/*.test.js',
  version: '1.147.0', // fix VS Code version
  workspaceFolder: process.cwd(),
  launchArgs: [
    '--disable-telemetry',
    '--disable-gpu',
    '--disable-extensions' // Disables other installed extensions that trigger background telemetry/auth checks
  ],
  mocha: {
    timeout: 60000
  }
});

