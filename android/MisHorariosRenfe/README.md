# TBC


# Maven

```shell
$ android delete avd -n test
$ echo no | android create avd --force -n test -t 'Google Inc.:Google APIs:10' --abi armeabi
$ emulator -avd test -no-skin -no-audio -no-window &
$ ./wait_for_emulator
$ mvn clean install -Pintegration-tests -Dandroid.device=test
```
