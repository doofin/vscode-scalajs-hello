# VSCode Extension in Scala.js

This is an update of [vscode-scalajs-hello](https://github.com/pme123/vscode-scalajs-hello) with Scala 3.3.3 and sbt.version=1.9.7.


## Your first Extension
A step-by-step tutorial for **Scala.js** using this example project.

Here is the original: [visualstudio.com/api/get-started](https://code.visualstudio.com/api/get-started/your-first-extension)

### Setup

* Install the extensions to develop: **Metals**, **Scala syntax**

* Clone this project

* Open the project in VSCode, run the `import build` task with Metals (it should display a popup automatically).

* Open the terminal, run  below : (The first time it may take a few minutes, because Scala will use "ScalablyTyped" to analyze the types of the VSCode extension API.
)
```
sbt compile
```


Below will open a new VSCode window with the extension loaded:

```
sbt open
```

* Run the Hello World command from the Command Palette (`⇧⌘P`) in the new VSCode window.
* Type `hello` and select `Hello World`.
  * You should see a Notification _Hello World!_.

## Project structure

The project uses the following:
* [SBT] build tool for building the project
* [Scala.js] for general coding
* [Scalably Typed] for JavaScript facades
* [scalajs-bundler] for bundling the JavaScript dependencies

SBT is configured with the `build.sbt` file. Scala.js, ScalablyTyped and the bundler are SBT plugins. With these, SBT manages your JavaScript `npm` dependencies. You should never have to run `npm` directly, simply edit the `npmDependencies` settings in `build.sbt`.

[accessible-scala]: https://marketplace.visualstudio.com/items?itemName=scala-center.accessible-scala
[helloworld-minimal-sample]: https://github.com/Microsoft/vscode-extension-samples/tree/master/helloworld-minimal-sample
[Scalably Typed]: https://github.com/ScalablyTyped/Converter
[SBT]: https://www.scala-sbt.org
[ScalaJS]: http://www.scala-js.org
[scalajs-bundler]: https://github.com/scalacenter/scalajs-bundler

## How do I code in Scala.js?

In general, javascript functions and classes can be used in the same way as in JS/TS!
If the typechecker disagrees, you can insert casts with `.asInstanceOf[Type]`.

The JS types (like `js.Array`) are available with
```scala
import scala.scalajs.js
```

The VSCode classes and functions are available with
```scala
import typings.vscode.mod as vscode
// to use:
vscode.function(arg)
```

Some additional types are available in the `anon` subpackage, for example:
```scala
import typings.vscode.anon.Dispose
vscode.commands.registerCommand(name, fun).asInstanceOf[Dispose]
```

You can find more information and tutorials on the [Scala.js website](https://www.scala-js.org/).
