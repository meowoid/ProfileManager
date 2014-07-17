## Profile Manager Documentation

### Using the Library

Add the library to your to your project. The profile data store singleton is accessed like this:

```
ProfileDataStore profileManager = ProfileDataStore.getInstance(context);
```

In order to add to and query from the data store.

The key operations that you can do with it are defined in the [ProfileInterface](https://github.com/xsenselabs/ProfileManager/blob/master/src/com/ubhave/profilemanager/ProfileInterface.java) class.

### Data Distributions

The library assumes that each distribution has a unique name (e.g., 'Fruits'). Each entry in a distribution is also a unique key (e.g., 'Bananas', 'Apples'), and has a particular value. You can increment the value of a distribution entry by using ```addToDistribution```. For example:

```
ProfileDataStore profileManager = ProfileDataStore.getInstance(context);
profileManager.addToDistribution('Fruits', 'Bananas', 12);
```

Will add 12 units to the 'Bananas' entry of the 'Fruits' distribution (note: this is adding, not setting!).

There are two ways that you can display a ```Distribution``` in your app:

* You can (dynamically) create a Distribution, and pass it to your Activity via an Intent. Your Activity has to extend ```AbstractIntentDistributionActivity```.
* You can start an Activity that queries the ```ProfileDataStore``` for a distribution. Your Activity has to extend ```AbstractStoredDistributionActivity```.

Both of these abstract views take care of asynchronously loading the data into a ListView. The adapter that your ListView uses has to extend ```DistributionListAdapter```.


### Data Events

### Data Mappings
