cd %~dp0\openrefine
start cmd /k cmd /c refine.bat
cd ..
call d:\tda2\bin\web-app-launcher 4444 %~dp0\proxy-web-root %~dp0\..\..\..\tdacloud
echo aaa
