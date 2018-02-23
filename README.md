# Aperture Browser
**Lafayette College CS305 Spring 2018.**

                   .,-:;//;:=,
               . :H@@@MM@M#H/.,+%;,
            ,/X+ +M@@M@MM%=,-%HMMM@X/,
          -+@MM; $M@@MH+-,;XMMMM@MMMM@+-
         ;@M@@M- XM@X;. -+XXXXXHHH@M@M#@/.
       ,%MM@@MH ,@%=             .---=-=:=,.
       =@#@@@MX.,                -%HX$$%%%:;
      =-./@M@M$                   .;@MMMM@MM:
      X@/ -$MM/                    . +MM@@@M$
     ,@M@H: :@:                    . =X#@@@@-     Apperture Browser
     ,@@@MMX, .                    /H- ;@M@M=     v1.0
     .H@@@@M@+,                    %MM+..%#$.     Copyright \u00A9 2018
      /MMMM@MMH/.                  XM@MH; =;
       /%+%$XHH@$=              , .H@@@@MX,
        .=--------.           -%H.,@@@@@MX,
        .%MM@@@HHHXX$$$%+- .:$MMX =M@@MM%.
          =XMMM@MM@MM#H;,-+HMM@M+ /MMMX=
            =%@M@M#@$-.=$@MM@@@M; %M%=
              ,:+$+-,/H#MMMMMMM@= =,
                    =++%%%%+/:-.";


> A simple HTTP web browser/web server implementation on top of a simulated network.

## Building the simulation

Build the project using the included Makefile as follows :

```bash
$ make
```

then run the server application, the proxy application and the client application (in that order)

```bash
$ make server
```

```bash
$ make proxy
```

```bash
$ make client
```

## Running the simulation

The simulation runs in three different modes: browser mode, experiment mode and verbose mode.

#### Browser mode

Browser mode is the default mode for running the client. It provides a browser with
rendering capabilities without showing any information about the network. To run the browser mode,
type:

```bash
$ make client
```

#### Experiment mode

In experiment mode, the client app adds the total time it took to render a page to the end of the screen.
Run experiment mode through:

```bash
$ make experimentClient
```

#### Verbose mode

In verbose mode, pages are not rendered. Instead, the browser will display the ongoing/outgoing HTTP requests
and responses and the time each of them took. Run verbose mode using:

```bash
$ make verboseClient
```


## Authors

- [Wassim Gharbi](https://github.com/wassgha)
- [Zura Mestiashvili](https://github.com/prosperi)
- [Erik Laucks](https://github.com/laucksy)

## License
Non-commercial, educational use only. Except with written authorization.
