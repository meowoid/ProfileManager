## Profile Manager Documentation

## Storing Data

The profile data store singleton is accessed like this:

```
ProfileDataStore profileManager = ProfileDataStore.getInstance(context);
```

In order to add to and query from the data store.

The key operations that you can do with it are defined in the [ProfileInterface](https://github.com/xsenselabs/ProfileManager/blob/master/src/com/ubhave/profilemanager/ProfileInterface.java) class.

### Data Distributions

The library assumes that each distribution has a unique name (e.g., 'Fruits'). Each entry in a distribution is also a unique key (e.g., 'Bananas', 'Apples'), and has a particular value.
You can increment the value of a distribution entry by using ```addToDistribution```. For example:

```
ProfileDataStore profileManager = ProfileDataStore.getInstance(context);
profileManager.addToDistribution('Fruits', 'Bananas', 12);
```

Will add 12 units to the 'Bananas' entry of the 'Fruits' distribution (note: this example is adding, not setting!).

### Data Events

### Data Mappings

## Viewing Data

### The Profile 'Home' View

If your app allows the user to track different kinds of variables (e.g., distributions for 'Fruits' and 'Vegetables'), then you may like
to have a profile 'home' activity that shows these.

This view can be added to your app by: (1) creating an Activity that extends ```AbstractProfileListActivity```, and (2) creating a JSON configuration file that
contains what profile entries should be shown. The library will, by default, be looking for a file called ```profile-list.json``` which has a single JSONArray
entry. For example:

```
{
	"profile":
	[
		{
		
		},
		{
		
		}
	]
}
```

### Data Distributions

There are two ways that you can display a ```Distribution``` in your app:

* You can (dynamically) create a Distribution, and pass it to your Activity via an Intent. Your Activity has to extend ```AbstractIntentDistributionActivity```.
* You can start an Activity that queries the ```ProfileDataStore``` for a distribution. Your Activity has to extend ```AbstractStoredDistributionActivity```.

Both of these abstract views take care of asynchronously loading the data into a ListView. The adapter that your ListView uses has to extend ```DistributionListAdapter```.


### Data Events

### Data Mappings
