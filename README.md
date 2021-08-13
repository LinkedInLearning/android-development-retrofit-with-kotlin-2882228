# Android Development: Retrofit with Kotlin
This is the repository for the LinkedIn Learning course Android Development: Retrofit with Kotlin. The full course is available from [LinkedIn Learning][lil-course-url].

![Android Development: Retrofit with Kotlin][lil-thumbnail-url] 

Many apps use your mobile phone’s internet connection to get data. On Android, the primary way that apps get and post data over the internet is with a library called Retrofit. This course shows you how to use Retrofit with idiomatic Kotlin code that you can apply directly to your Android app. Instructor Rahul Pandey covers everything you need to know to build rich, networked Android apps. Rahul starts with helping you understand APIs and their uses. He explains concurrency, coroutines in Kotlin, and how both can benefit your app. Rahul steps you through getting started with an Android Retrofit project, as well as retrieving and sending data and handling authentication in your Android app. Finally, Rahul covers several advanced configuration options related to Retrofit.

## Instructions
This repository has branches for each of the videos in the course. You can use the branch pop up menu in github to switch to a specific branch and take a look at the course at that stage, or you can add `/tree/BRANCH_NAME` to the URL to go to the branch you want to access.

## Branches
The branches are structured to correspond to the videos in the course. The naming convention is `CHAPTER#_MOVIE#`. As an example, the branch named `02_03` corresponds to the second chapter and the third video in that chapter. 
Some branches will have a beginning and an end state. These are marked with the letters `b` for "beginning" and `e` for "end". The `b` branch contains the code as it is at the beginning of the movie. The `e` branch contains the code as it is at the end of the movie. The `main` branch holds the final state of the code when in the course.

When switching from one exercise files branch to the next after making changes to the files, you may get a message like this:

    error: Your local changes to the following files would be overwritten by checkout:        [files]
    Please commit your changes or stash them before you switch branches.
    Aborting

To resolve this issue:
	
    Add changes to git using this command: git add .
	Commit changes using this command: git commit -m "some message"


### Instructor

Rahul Pandey 
                                                   

Check out my other courses on [LinkedIn Learning](https://www.linkedin.com/learning/instructors/rahul-pandey).

[lil-course-url]: https://www.linkedin.com/learning/android-development-retrofit-with-kotlin
[lil-thumbnail-url]: https://cdn.lynda.com/course/2882228/2882228-1628621457878-16x9.jpg
