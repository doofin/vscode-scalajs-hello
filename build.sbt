import scala.sys.process.*
import org.scalajs.linker.interface.{ModuleKind, ModuleInitializer, ModuleSplitStyle}

val outdir = "out" // output directory for the extension
// open command in sbt
lazy val open = taskKey[Unit]("open vscode")
def openVSCodeTask: Def.Initialize[Task[Unit]] =
  Def
    .task[Unit] {
      val base = (ThisProject / baseDirectory).value
      val log = (ThisProject / streams).value.log

      val path = base.getCanonicalPath
      // install deps to out dir
      // print info with orange color
      println("\u001b[33m" + "[copying] package.json to out dir" + "\u001b[0m")
      s"cp package.json ${outdir}/package.json" ! log
      if (!(base / outdir / "node_modules").exists) {
        println("\u001b[33m" + "[installing] dependencies into out dir with npm" + "\u001b[0m")
        s"npm install --prefix ${outdir}" ! log
      } else {
        println("\u001b[33m" + "[skipping] dependencies installation" + "\u001b[0m")
      }
      // launch vscode
      s"code --extensionDevelopmentPath=$path" ! log
      ()
    }
  // .dependsOn(installDependencies)

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
    testFrameworks += new TestFramework("utest.runner.Framework")
    // publishMarketplace := publishMarketplaceTask.dependsOn(fullOptJS in Compile).value
    // emit ES module like import { Foo } from "bar.js";
    // scalaJSLinkerConfig ~= {
    //   _.withModuleKind(ModuleKind.ESModule)

    // },
    // webpackBundlingMode := BundlingMode.LibraryAndApplication()
    // scalaJSModuleKind ~= ModuleKind.ESModule
  )
addCommandAlias("compile", ";fastOptJS")

/* lazy val installDependencies = Def.task[Unit] {
  val base = (ThisProject / baseDirectory).value
  val log = (ThisProject / streams).value.log
  if (!(base / "node_module").exists) {
    val pb =
      new java.lang.ProcessBuilder("npm", "install")
        .directory(base)
        .redirectErrorStream(true)

    pb ! log
  }
} */
