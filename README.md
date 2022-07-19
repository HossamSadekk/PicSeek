## Images Search App 
In this project i have built an app that using Kotlin , MVVM , Navigation Component , Glide , Hilt , Coroutine , Paging3 , Retrofit , SavedStateHandle.
I have focused on dependency injection specially using dagger-hilt.

# Dependency Injection
![App Screenshot](https://blog.yudiz.com/wp-content/uploads/2020/09/Hilt-A-new-and-easy-way-to-use-Dagger.jpg)

### What is Dependency Injection..?
DI allows you to inject objects into a class, rather than relying on the class to create the object itself.

For Example .. we have a class A and class B , class A uses class B then A depends upon the class B
(Now The class A is dependants and class B is dependency)

```java
  class A {
        
        public A() {
                //Each  time we use class A,
                // we need to create B instance
                B b = new B();
                b.callMethod();
        }
}
```

#### Okay what is the problem???
- If you have so many dependencies within a specific method or class and there's an error it will be hard to test your code.

- When classes or methods are coupled and you want to change anything in your code then u will change all your code in that class and you won't achieve reusablility.

________
________
________

### What is the Inversion of Control (IOC)..?
It is a principle that dependency injection works on , in a simpler words your code can't instantiate another class using "new" keyword instead you have to supply the object form outside.

## What is Hilt..??
Hilt is a library that defines a way to do DI , it is built on top of Dagger to simplify it.
##  Hilt Vs Dagger2
Dagger2 have an amount of boilerplate code and also it is very complicated comparing to hilt!.

*In Dagger2 we usually create a component class and include it our modules and Builder interface in dagger-hilt we just use annotation @HiltAndroidApp
on our Application and hilt will do everything by itself, that's mean that hilt does'nt create a component class and rather than it have a built-in set of components and generates a base class for your application that uses those generated components.

*In Dagger2 If you want an instance the dependant tell daggerComponent that it want that instance from the dependency and dagger know as well that dependencies through @Inject, here we write that @Inject annotation in our code what if we do not own that class or it is a library .. in that case we must use @Module and @provides.

@Module here work when dependant tell the daggerComponent that he want the instance daggerComponent then call Module to get that instance from the dependency and the dependency provide that instance through @provides.

In Hilt it have also a module class annotated with @Module to provide dependencies, you must add more annotation @InstallIn to scope our where module will be used or installed in, we here provides one of built-in set of components like `SingletonComponent` it is a Hilt component that has the lifetime of the application.
![App Screenshot](https://i.stack.imgur.com/IcFKi.png)

*In Dagger2 if you want to @Inject something in the MainActivity, you must create a method to tell dagger to provide all the dependencies that MainActivity requests.
```java
@Component
public interface AppComponent {
    void inject(MainActivity mainActivity);
}
```
Hilt provide us the annotation @AndroidEntryPoint that do the job as `void inject(MainActivity mainActivity);` method , just in one annotation.


*In Dagger2 if our module have more than dependency of the same return type it does not know which is the right dependency that dependant wants and it will cause an error , actually this happen also in hilt and we should use @Named() annotation to hilt know which value must return. 
###  Paging3 
It is a Jetpack library that help us to manage a large amount of data in recyclerview and load them efficiently, It supports Flow, LiveData, and RxJava along with Kotlin Coroutine.

![App Screenshot](https://miro.medium.com/max/875/1*2hTWZMNuTiU1crl3VPxFVw.png)

- Remote Mediator: We use it when we want to cache data in Room Database , in this case our local db will be the source for the paging adapter.

- Paging Source : It is responsible for retrieving data from a data source like a network call or room db ,.. for an instance of `PagingData`

`abstract class PagingSource<Key : Any?, Value : Any?>` the key defines how we will load our pages like int number refer for the state of our page , the type is the type of data that will be loaded.

- Paging Data : Object contain the data that is paginated from the paging source.

- Pager : Based on paging source and PagingConfig the page consumes what remote mediator or paging source returns , it construct an instance of paging data.

- Paging Config : It manage the behavior of pagination like the maximum number of items that may be loaded into PagingData and requested load size for initial load from PagingSource.

- PagingDataAdapter : It responsible for putting data in our recyclerview by consuming this data from the paging data 

### viewLifecycleOwner vs lifecycleOwner
Using viewLifecycleOwner rather than lifecycleOwner in fragment to start listen to the live data streams because the fragment itself lives longer than the fragment's view.
So live data gets destroyed when lifecycleOwner also gets destroyed and that is wrong in our case because observer will still exist after onDestroyView(), despite the view being destroyed.

### Avoiding Memory Leak
Avoiding memory leak of view binding that happen because the fragment instance still has a strong reference to the view even after the view is destroyed throug null out the references in the onDestroyView().


### Death Process With SavedStateHandle 
In this project if we searched for specific query and go through to a detail of an image and death process happend when we navigate back to the gallery fragment we will see the initial state of the app not the previous state we have been searched for because the device is running low on memory and needs to quickly free some up.

ViewModel is not destroyed on configuration changes but destroyed when the app process killed, and to make ViewModel survive this situation i have to use `SavedStateHandle`.

While i have used `@HiltViewModel` rather than `@ViewModelInject` i did not care about how to inject SavedStateHandle instance with `@Assisted`.

