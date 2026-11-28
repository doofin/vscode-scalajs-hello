# VS Code extensions with Scala.js
This repository demonstrates how to build VS Code extensions using Scala.js, including examples of:
- commands
- inline completions
- code actions
- language-client integration
- syntax highlighting

And also testing infrastructure:
- basic unit tests with MUnit under `src/test/scala`
- integration tests with VS Code extension host under `src/inteTest`
see [docs/test.md](./docs/test.md) for details.

WIP:
- language configuration(Comment toggling,Brackets definition,etc)

## Get started

Requirements: [Node.js/npm](https://nodejs.org/) and [sbt](https://www.scala-sbt.org/).

1. Clone the repository and open it in VS Code. Let Metals import the sbt build.
2. Run `sbt open` in the project root. This compiles the extension and opens a VS Code Extension Development Host. The first run may take a while.
3. In the new window, open the Command Palette, run **Hello World**, and
   confirm the notification appears.
4. To try the language server, open a Plain Text document, type `AAA`, and
   confirm the warning appears. Request completion to see TypeScript and
   JavaScript suggestions.

## Build and package

Compile the extension and prepare its language server:
```bash
sbt fastOptJS prepareExtension
```

To create a `.vsix` package:
```bash
cd out &&  npx vsce package
```

## Testing
For MUnit unit tests, use `sbt test`. For details on the Scala.js integration
test setup, see [docs/test.md](./docs/test.md).


Run the integration test(involving the vscode api) with `npm test`. It checks
that the extension activates and registers the Hello World command. 

## Usage
### Use it as a template
click on the `Use this template` button to create a new repository with the same structure in github.

### Use it as sbt dependency
In your `build.sbt` add the following:
```scala
lazy val vsc = RootProject(uri("https://github.com/doofin/vscode-scalajs-hello.git")) 
lazy val root = Project("root", file(".")) dependsOn(vsc)
```

### Use it as a library
publish local and use it as a library in your project:
```bash
sbt publishLocal
```

Then add
```scala
"com.github" % "vscextension" % "0.1.0-SNAPSHOT"
```


or use jitpack to include it as a dependency.
**Currently not working** due to jitpack missing npm! Welcome to contribute to fix it.

You can use this project as a library in your project by adding the following to your `build.sbt`:
```scala
resolvers += Resolver.bintrayRepo("jitpack", "https://jitpack.io")
libraryDependencies += "com.github" % "vscode-scalajs-hello" % "master-SNAPSHOT" // might be wrong
```

You can find the latest version on
[jitpack.](https://jitpack.io/#doofin/vscode-scalajs-hello)

Note: 
 - I recommend using the Metals extension for Scala in VSCode.
 - If you have any issues, please open an issue on this repository.

## Project structure
The project file structure in src/main/scala is as follows:
```bash
src/main/scala
├── extensionMain.scala // main entry point for the extension
├── commands.scala, codeActions.scala,etc // files for different extension features
│   ├── facade // facade for extra vscode api
│   ├── io // file and network io functions
src/test/scala // unit tests with MUnit
src/inteTest/scala // integration tests with VS Code api
```

## How to code in Scala js?

In general, javascript functions and classes can be used in the same way as in JS/TS!
If the typechecker disagrees, you can insert casts with `.asInstanceOf[Type]`.

The JS types (like `js.Array`) are available from
```scala
import scala.scalajs.js
```

The VSCode classes and functions are available from
```scala
import typings.vscode.mod as vscode

vscode.window.showInformationMessage("Hello World!")
```

Some additional types are available in the `anon` subpackage, for example:
```scala
import typings.vscode.anon.Dispose
// register a command. The cast is necessary due to typescript conversion limitations.
vscode.commands.registerCommand(name, fun).asInstanceOf[Dispose]
```

You can find more information and tutorials on the [Scala.js website](https://www.scala-js.org/).

# references:
 - original [vscode-scalajs-hello](https://github.com/pme123/vscode-scalajs-hello) with Scala 3.3.3 and sbt.version=1.9.7.
 - [VSCode Extension Samples](https://github.com/microsoft/vscode-extension-samples) repository.
 - [visualstudio.com/api/get-started](https://code.visualstudio.com/api/get-started/your-first-extension) in typescript.
 - [scalablytyped.com](https://scalablytyped.org/docs/plugin) for the typing plugin.
 - [scala js](https://www.scala-js.org/doc/project/) for the scala.js project.
 - [SBT](https://www.scala-sbt.org) build tool for building the project
 - [scalajs-bundler](https://github.com/scalacenter/scalajs-bundler) for bundling the JavaScript dependencies

# contribute
Welcome to contribute to this project! Here are some ideas:
- Add more examples for vscode extension features.
- Add github actions for automatic release 
- Add extension-host integration tests.
