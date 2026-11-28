# improving and enhancing this template
To help developers write vscode extensions efficiently, we already provide some common utilities and facades:
- VSCode API facade by scalajs converter plugin
- Some examples of how to use the VSCode API facade
- language client facade


Examples inspired by the reference extensions:
- Agda: semantic highlighting, Unicode input, goal/type queries, and goal navigation.
- Metals: run/debug code lenses, test discovery, and executable worksheets.

Reusable utilities can cover command registration, configuration, file/network IO, status-bar items, and diagnostic decorations.

Reference vscode plugins:
- https://github.com/banacorn/agda-mode-vscode
- https://github.com/scalameta/metals-vscode

Principles of documentation editing
- don't delete the instructions
- only concisely describe the result
- be conservative when editing documentation

## Rounds of improvement
1st round instructions:
- visit the provided reference vscode plugins for inspiration about what example scenarios and utilities can be included in this documentation, make it brief.
- add syntax highlighting example code for this scalajs project

2nd round instructions:
- why the gen typings.vscodeLanguageclient is weired? the auto converter src is at src-ref/converter . briefly analyze and explain to me
- check node_modules/vscode-languageclient/lib/common/client.d.ts and make Middleware typings better for the LanguageClient facade
