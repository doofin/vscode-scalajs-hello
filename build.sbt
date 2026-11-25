import scala.sys.process.*
import org.scalajs.linker.interface.{ModuleKind, ModuleInitializer, ModuleSplitStyle}

val mScalaVersion = "3.3.9"
val extOutdir = "out" // output directory for the extension
// open command in sbt
lazy val open = taskKey[Unit]("open vscode")
lazy val prepareExtension = taskKey[Unit]("prepare extension output")

addCommandAlias("compile", ";fastOptJS")
addCommandAlias("dev", "~fastOptJS")
addCommandAlias("fix", ";scalafixEnable;scalafixAll;")

lazy val root = project
  .in(file("."))
  .enablePlugins(
    ScalaJSPlugin,
    ScalaJSBundlerPlugin,
    ScalablyTypedConverterPlugin
  )
  .settings(
    moduleName := "vscextension",
    organization := "com.doofin",
    scalaVersion := mScalaVersion,
    // warn unused imports and vars
    scalacOptions ++= Seq(
      "-Wunused:all"
    ),
    // check if it is running in test
    // testOptions += Tests.Setup(_ => sys.props("testing") = "true"),
    Compile / fastOptJS / artifactPath := baseDirectory.value / "out" / "extension.js",
    Compile / fullOptJS / artifactPath := baseDirectory.value / "out" / "extension.js",
    libraryDependencies += "org.scalameta" %%% "munit" % "1.0.4" % Test,
    Compile / npmDependencies ++=
      Seq(
        "@types/vscode" -> "1.96.0", //
        "@types/node" -> "16.11.7", // ts 3.7
        "@types/node-fetch" -> "2.5.12", // ts 3.7,compile error for scalablytyped
        "@vscode/prompt-tsx" -> "0.3.0-alpha.18",
        "vscode-languageclient" -> "9.0.1" // working with manuallly created facade
      ),
    /* ++ // check if it is running in test
        (if (sys.props.get("testing") != Some("true"))
           Seq(
             "@types/vscode" -> "1.96.0"
           )
         else Seq.empty), */
    // don't generate types with scalablytyped
    stIgnore ++= List(),
    prepareExtension := prepareExtensionTask().value,
    open := openVSCodeTask().dependsOn(Compile / fastOptJS, prepareExtension).value
  )

lazy val integrationTest = project
  .in(file("integrationTest"))
  .enablePlugins(ScalaJSPlugin)
  .dependsOn(root)
  .settings(
    moduleName := "vscextension-test",
    scalaVersion := mScalaVersion,
    Compile / sourceDirectory := baseDirectory.value.getParentFile / "src" / "inteTest",
    Compile / mainClass := Some("vscextension.IntegrationTestMain"),
    scalaJSUseMainModuleInitializer := true,
    scalaJSLinkerConfig ~= (_.withModuleKind(ModuleKind.CommonJSModule)),
    Compile / fastOptJS / artifactPath :=
      baseDirectory.value.getParentFile / extOutdir / "test" / "extension.test.js",
    libraryDependencies += "org.scalameta" %%% "munit" % "1.0.4"
  )

def openVSCodeTask(openVscode: Boolean = true): Def.Initialize[Task[Unit]] =
  Def
    .task[Unit] {
      val base = (ThisProject / baseDirectory).value
      val log = (ThisProject / streams).value.log

      val path = base.getCanonicalPath
      // launch vscode
      if (openVscode) {
        mLog("[opening] vscode")
        s"code --extensionDevelopmentPath=$path" ! log
      }
      ()
    }

def mLog(message: String): Unit = println(s"\u001b[33m[log] ${message}\u001b[0m")

def prepareExtensionTask(): Def.Initialize[Task[Unit]] =
  Def.task[Unit] {
    val base = (ThisProject / baseDirectory).value
    val log = (ThisProject / streams).value.log
    val output = base / extOutdir

    IO.createDirectory(output / "server")
    IO.createDirectory(output / "test")
    IO.copyFile(base / "package.json", output / "package.json")
    IO.copyFile(base / "server" / "server.js", output / "server" / "server.js")
    mLog(s"Copied server.js to output directory: ${output.getAbsolutePath}")

    def run(command: Seq[String]): Unit = {
      val exitCode = Process(command, base).!(log)
      if (exitCode != 0) sys.error(s"Command failed ($exitCode): ${command.mkString(" ")}")
    }

    run(Seq("npm", "pkg", "set", "main=./extension", "--prefix", output.getAbsolutePath))
    run(
      Seq(
        "npm",
        "install",
        "--prefix",
        output.getAbsolutePath,
        "--no-audit",
        "--no-fund"
      )
    )
  }
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
