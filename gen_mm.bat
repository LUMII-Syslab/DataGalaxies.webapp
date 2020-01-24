rd /s src\lv\lumii\datagalaxies\mm
call ..\..\bin\mmd2java.bat GalaxyEngineMetamodel.mmd src lv.lumii.datagalaxies.mm

rd /s src\lv\lumii\datagalaxies\eemm
call ..\..\bin\ecore2java EEMM.ecore src
