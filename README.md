## Profile Manager Documentation

#### What is the Profile Manager library?

The Profile Manager library was developed to facilitate keeping track of user stats in an Android application. The library supports three kinds of stats:

* **Distributions**. A [frequency distribution](http://en.wikipedia.org/wiki/Frequency_distribution) is a group of variables, where each variable has a particular frequency count. For example, the distribution could be: "Home": 20, "Work": 80.
* **Events**. We define an event as a HashMap (or JSON object) with key-value pairs that occurs at a particular time. Events are queried for by the time stamp of when they were created.
* **Mappings**. A mapping stores a user variable that has a static value. For example, a mapping could be: "Home": "London". 

The library has also implemented a number of abstract views to display these stats.

#### System Requirements
This library is available for all Android applications that are running version 2.3.3 and above (it has not been tested for apps below this version).

Please note that this library has been built and tested using Android phones, and not the Android Emulator. We currently do not support development via the emulator.

#### Using the Library

Read the library [docs](https://github.com/xsenselabs/ProfileManager/blob/master/docs/), and the [Example App](https://github.com/xsenselabs/ExampleProfileManager) repository.

#### Questions and Contact

If you have any questions, please email [this google group](https://groups.google.com/forum/#!forum/es-library-developers).

#### Research Using the Sensor Manager

See [here](https://github.com/nlathia/SensorManager/blob/master/docs/research.md) for research papers that use one or more of the X-Sense libraries.

This library was initially developed as part of the [EPSRC Ubhave](http://ubhave.org/) (Ubiquitous and Social Computing for Positive Behaviour Change) Project, and was first used in the [Emotion Sense](http://emotionsense.org/) application.

## Authors & Contributors

* Neal Lathia ([nlathia](https://github.com/nlathia)), University of Cambridge, UK

## License
Copyright (c), 2014 University of Cambridge

Permission to use, copy, modify, and/or distribute this software for any
purpose with or without fee is hereby granted, provided that the above
copyright notice and this permission notice appear in all copies.

THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR
IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.

More information available [here](http://en.wikipedia.org/wiki/BSD_licenses).
