import scala.sys.process.*
import org.scalajs.linker.interface.{ModuleKind, ModuleInitializer, ModuleSplitStyle}

lazy val installDependencies = Def.task[Unit] {
  val base = (ThisProject / baseDirectory).value
  val log = (ThisProject / streams).value.log
  if (!(base / "node_module").exists) {
    val pb =
      new java.lang.ProcessBuilder("npm", "install")
        .directory(base)
        .redirectErrorStream(true)

    pb ! log
  }
}

// open command in sbt
lazy val open = taskKey[Unit]("open vscode")

def openVSCodeTask: Def.Initialize[Task[Unit]] =
  Def
    .task[Unit] {
      val base = (ThisProject / baseDirectory).value
      val log = (ThisProject / streams).value.log

      // launch vscode
      val path = base.getCanonicalPath
      s"code --extensionDevelopmentPath=$path" ! log
      ()
    }
    .dependsOn(installDependencies)
addCommandAlias("compile", ";fastOptJS")
lazy val root = project
  .in(file("."))
  .enablePlugins(
    ScalaJSPlugin,
    ScalaJSBundlerPlugin,
    ScalablyTypedConverterPlugin
  )
  .settings(
    scalaVersion := "3.3.4",
    // warn unused imports and vars
    scalacOptions ++= Seq(
      "-Wunused:all"
    ),
    moduleName := "vscode-scalajs-hello",
    Compile / fastOptJS / artifactPath := baseDirectory.value / "out" / "extension.js",
    Compile / fullOptJS / artifactPath := baseDirectory.value / "out" / "extension.js",
    libraryDependencies ++= Seq(
      "com.lihaoyi" %%% "utest" % "0.8.2" % "test"
    ),
    Compile / npmDependencies ++=
      Seq(
        "@types/vscode" -> "1.96.0", //
        "vscode-languageclient" -> "9.0.1" // not working
      ),
    open := openVSCodeTask.dependsOn(Compile / fastOptJS).value,
    // open := openVSCodeTask.dependsOn(Compile / fastOptJS / webpack).value,
    testFrameworks += new TestFramework("utest.runner.Framework"),
    // publishMarketplace := publishMarketplaceTask.dependsOn(fullOptJS in Compile).value
    // emit ES module like import { Foo } from "bar.js";
    // scalaJSLinkerConfig ~= {
    //   _.withModuleKind(ModuleKind.ESModule)

    // },
    webpackBundlingMode := BundlingMode.LibraryAndApplication()
    // scalaJSModuleKind ~= ModuleKind.ESModule
  )
