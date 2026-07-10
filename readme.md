# mslearn-github-copilot-dev-java

## Purpose

This repository is the Java-only split of MicrosoftLearning/mslearn-github-copilot-dev.

## Included Content

- .devcontainer/devcontainer.json (Java devcontainer)
- AccelerateDevGHCopilot (copied from module 3 Java starter)
- Instructions/readme.md
- Instructions/Concepts
- Instructions/Labs selected files:
  - LAB_AK_00_configure_github_copilot_sdk_lab.md
  - LAB_AK_00_configure_github_dev_kit_lab.md
  - LAB_AK_00_configure_lab_environment.md
  - LAB_AK_00_configure_lab_environment_py.md
  - LAB_AK_00_enable_github_copilot_in_visual_studio_code.md
  - LAB_AK_01_examine_settings_interface.md
  - LAB_AK_02_analyze_document_code_java.md
  - LAB_AK_03_develop_code_features_java.md
  - LAB_AK_04_develop_unit_tests_junit.md
  - LAB_AK_05_refactor_improve_existing_code_java.md

## Exclusions

- Non-Java lab variants
- Instructions/Labs/Media
- C# and Python project variants

## AccelerateDevGHCopilot Content Consistency Check

Use the following PowerShell commands to compare all source DownloadableCodeProjects/\*/AccelerateDevGHCopilot folders.

```powershell
$src = 'C:/repo/mslearn-github-copilot-dev/DownloadableCodeProjects'
$folders = Get-ChildItem -Path $src -Directory | ForEach-Object {
  Join-Path $_.FullName 'AccelerateDevGHCopilot'
} | Where-Object { Test-Path $_ }

$manifests = @{}
foreach ($folder in $folders) {
  $manifest = Get-ChildItem -Path $folder -Recurse -File |
    ForEach-Object {
      $rel = $_.FullName.Substring($folder.Length + 1).Replace('\\','/')
      $hash = (Get-FileHash -Path $_.FullName -Algorithm SHA256).Hash
      "${rel}|${hash}"
    } | Sort-Object
  $manifests[$folder] = $manifest
}

$baseline = $folders[0]
foreach ($folder in $folders | Select-Object -Skip 1) {
  $diff = Compare-Object -ReferenceObject $manifests[$baseline] -DifferenceObject $manifests[$folder]
  if ($diff) {
    Write-Host "DIFF: $baseline vs $folder"
    $diff | Select-Object -First 25
  } else {
    Write-Host "IDENTICAL: $baseline vs $folder"
  }
}
```

Note: Differences are expected across module and language variants. Review differences before creating additional language split repos.
