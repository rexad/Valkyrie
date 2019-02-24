import BuildSettings._
import Dependencies._
import com.typesafe.sbt.packager.docker._
import sbt.Keys._
import sbt._

wartremoverErrors ++= Warts.unsafe

lazy val testAll: TaskKey[Unit] = TaskKey[Unit]("test-all")

lazy val commons = SbtLibraryProject("Valkyrie-commons")
  .settings(
    libraryDependencies ++= commonsDependencies ++ testDependencies
  )

lazy val processor = SbtAppProject("Valkyrie-processor")
  .settings(
    parallelExecution in ThisBuild := false,
    /* coverageExcludedPackages := ".*hermes\\.fsm\\.model\\..*;" +
                                ".*hermes\\.fsm\\.db\\.Databases", */
    libraryDependencies ++= processorDependencies ++ testDependencies
  )    .dependsOn(commons % "compile->compile;test->test")

lazy val harvester = SbtAppProject("Valkyrie-harvester")
  .settings(
    parallelExecution in ThisBuild := false,
    libraryDependencies ++= harvesterDependencies ++ testDependencies
  )    .dependsOn(commons % "compile->compile;test->test")

lazy val api = PlayProject("Valkyrie-api")
  .settings(
    libraryDependencies ++= apiDependencies,
    routesImport += "utils.QueryBinders._",
    // Play Framework specific excludes
    javaOptions in Docker ++= Seq("-Dconfig.file=conf/docker.conf"),
    packageName in Docker := "api-functional",
    dockerExposedPorts := Seq(9000, 1433, 5671, 5672),
    dockerCommands ++= Seq(
      // setting the run script executable
      ExecCmd("RUN",
        "chmod", "u+x",
        s"${(defaultLinuxInstallLocation in Docker).value}/bin/${executableScriptName.value}"),
      // setting a daemon user
      Cmd("USER", "daemon")
    )
  )
  .configs(IntegrationTest)
  .settings(Defaults.itSettings: _*)
  .settings(
    fork in Test := true,
    fork in IntegrationTest := true,
    sourceDirectory in IntegrationTest := baseDirectory.value / "integration",
    javaSource in IntegrationTest := baseDirectory.value / "integration",
    scalaSource in IntegrationTest := baseDirectory.value / "integration",
    testAll := (test in IntegrationTest).dependsOn(test in Test).value
  )
  .dependsOn(commons)

lazy val root = Project("Valkyrie", file("."))
  .settings(commonSettings: _*)
  .settings(codeCoverageSettings: _*)
  .dependsOn(commons)
  .aggregate(commons, api, processor, harvester)
  .enablePlugins(sbtdocker.DockerPlugin, DockerComposePlugin)