import scala.sys.process.*
import org.scalajs.linker.interface.{ModuleKind, ModuleInitializer, ModuleSplitStyle}

val outdir = "out" // output directory for the extension
// open command in sbt
lazy val open = taskKey[Unit]("open vscode")

lazy val root = project
  .in(file("."))
  .enablePlugins(
    ScalaJSPlugin,
    ScalaJSBundlerPlugin,
    ScalablyTypedConverterPlugin
  )
  .configs(IntegrationTest)
  .settings(Defaults.itSettings: _*)
  .settings(inConfig(IntegrationTest)(ScalaJSPlugin.testConfigSettings): _*)
  .settings(
    moduleName := "vscextension",
    organization := "com.doofin",
    scalaVersion := "3.3.4",
    // warn unused imports and vars
    scalacOptions ++= Seq(
      "-Wunused:all"
    ),
    // check if it is running in test
    // testOptions += Tests.Setup(_ => sys.props("testing") = "true"),
    Compile / fastOptJS / artifactPath := baseDirectory.value / "out" / "extension.js",
    Compile / fullOptJS / artifactPath := baseDirectory.value / "out" / "extension.js",
    libraryDependencies ++= Seq(
      // "com.lihaoyi" %%% "utest" % "0.8.2" % "test",
      "org.scalameta" %%% "munit" % "0.7.29" % Test
    ),
    Compile / npmDependencies ++=
      Seq(
        "@types/vscode" -> "1.96.0", //
        "@types/node" -> "16.11.7", // ts 3.7
        "@types/node-fetch" -> "2.5.12", // ts 3.7,compile error for scalablytyped
        "vscode-languageclient" -> "9.0.1" // working with manuallly created facade
      ),
    /* ++ // check if it is running in test
        (if (sys.props.get("testing") != Some("true"))
           Seq(
             "@types/vscode" -> "1.96.0"
           )
         else Seq.empty), */
    stIgnore ++= List( // don't generate types with scalablytyped
    ),
    open := openVSCodeTask().dependsOn(Compile / fastOptJS).value
    // open := openVSCodeTask.dependsOn(Compile / fastOptJS / webpack).value,
    // testFrameworks += new TestFramework("utest.runner.Framework")
    // publishMarketplace := publishMarketplaceTask.dependsOn(fullOptJS in Compile).value
  )
addCommandAlias("compile", ";fastOptJS")
addCommandAlias("dev", "~fastOptJS")
addCommandAlias("fix", ";scalafixEnable;scalafixAll;")

def openVSCodeTask(openVscode: Boolean = true): Def.Initialize[Task[Unit]] =
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
      if (openVscode) {
        println("\u001b[33m" + "[opening] vscode" + "\u001b[0m")
        s"code --extensionDevelopmentPath=$path" ! log
      }
      ()
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
