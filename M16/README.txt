Hello
Please note that the 'bat' files should work only if your javafx path is saved in your environmental variables as 'JAVA_FX'
(which is the name of my javafx path variable),
eg 'java --module-path %JAVA_FX% --add-modules javafx.controls,javafx.fxml ClientApplication'.
If you have javafx path saved to different name: switch 'JAVA_FX' to the name of your javafx variable.
If you dont have javafx saved to a variable at all: switch '%JAVA_FX%' to your javafx library path,
such as 'C:\Users\nir\javafx-sdk-18\lib'.

To sum up, open bat file as text file and switch to the appropriate javafx path.