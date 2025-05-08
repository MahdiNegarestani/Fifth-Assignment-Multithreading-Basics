# [ThreadDemo.java](assets/ThreadDemo.java)

 Fifth Assignment: Multithreading Basics

## Table of contents

- [Introduction](#introduction)
- [Objectives 🎯](#objectives-)
- [Theoretical Questions 📝](#theoretical-questions-)
- [Practical Questions 💻](#practical-questions-)
- [Bonus Tasks 🌟](#bonus-tasks-)
- [Evaluation ⚖️](#evaluation-)
- [Submission ⌛](#submission-)
- [Additional Resources 📚](#additional-resources-)

## Important Note:

This project is configured to use a **local Gradle installation**. If you're opening this project on your own system, please make sure to:

1. Go to **Settings/Preferences** > **Build, Execution, Deployment** > **Build Tools** > **Gradle**.
2. Under **Gradle settings**, change the **Gradle distribution** to:

- **Use local Gradle distribution**, and
- Set the **Gradle home** path to your own local Gradle installation directory.

If you don’t have Gradle installed locally, you can either:

- Install Gradle manually and configure the path, or
- Change the setting to **Use Gradle wrapper** instead.

## Introduction

Welcome to your Fifth Advanced Programming (AP) Assignment. This project is divided into two main sections:

1. **Theoretical Questions**: This section is designed to deepen your understanding of key multithreading concepts in Java. You'll have to analyze three code blocks and answer questions about them.
2. **Practical Questions**: In this section, you'll get hands-on experience with multithreading in Java. Your code will be manually checked to ensure you've implemented the tasks using multithreading.

## Objectives 🎯

By completing this assignment, you will:

- Deepen your understanding of **multithreading** in Java and apply the concepts effectively.
- Gain familiarity with key multithreading concepts.

Note that while this assignment covers many important aspects of multithreading, there are some advanced topics such as race condition and synchronization that won't be covered in this assignment and will be introduced in the following week. However, a solid understanding of the concepts covered in this assignment is crucial for grasping those advanced topics.

## Theoretical Questions 📝

**Note: Please answer these questions in a Markdown file (Report.md) and place it in the root directory of your fork. Include code or screenshots where you see fit.**

### 1. `start()` vs `run()`

```java
public class StartVsRun {  
    static class MyRunnable implements Runnable {  
        public void run() {  
            System.out.println("Running in: " + Thread.currentThread().getName()); 
        }  
    }  
    public static void main(String[] args) throws InterruptedException {  
        Thread t1 = new Thread(new MyRunnable(), "Thread-1");  
        System.out.println("Calling run()");  
        t1.run();  
        Thread.sleep(100);  
  
        Thread t2 = new Thread(new MyRunnable(), "Thread-2");  
        System.out.println("Calling start()");  
        t2.start();  
    }  
}  
```

**Questions:**

- What output do you get from the program? Why?
- What’s the difference in behavior between calling `start()` and `run()`?

---

### 2. Daemon Threads

```java
public class DaemonExample {  
    static class DaemonRunnable implements Runnable {  
        public void run() {  
            for(int i = 0; i < 20; i++) {  
                System.out.println("Daemon thread running...");  
                try {  
                    Thread.sleep(500);  
                } catch (InterruptedException e) {  
                 //[Handling Exception...]  
                }          
            }  
        }  
    }  
    public static void main(String[] args) {  
        Thread thread = new Thread(new DaemonRunnable());  
        thread.setDaemon(true);  
        thread.start();  
        System.out.println("Main thread ends.");  
    }  
}  
```

**Questions:**

- What output do you get from the program? Why?
- What happens if you remove `thread.setDaemon(true)`?
- What are some real-life use cases of daemon threads?

---

### 3. A shorter way to create threads

```java
public class ThreadDemo {  
    public static void main(String[] args) {  
        Thread thread = new Thread(() -> {  
            System.out.println("Thread is running using a ...!");  
        });  
  
        thread.start();  
    }  
}   
```

**Questions:**

- What output do you get from the program?
- What is the `() -> { ... }` syntax called?
- How is this code different from creating a class that extends `Thread` or implements `Runnable`?

## Practical Questions 💻

### Typing Test

#### Task Description

The application will display words to the user one at a time, with a set timeout for each word. If the user does not type the word within the specified timeout, the opportunity is lost, and the app will automatically move on to the next word. The input field must remain available at all times, meaning the timeout will not pause for user input. If the user types the word correctly or incorrectly before the timeout expires, the program will immediately display the result and proceed to the next word, ignoring any remaining time.

#### 🛠 What  You  Need to Do

- Currently, some test words are hardcoded; instead, you must:
  - Read words from the provided file containing 100 words.
  - Each time, randomly select words from this list for testing.
- After the test is completed:
  - Show the total number of correct and incorrect words.
  - Show the total time taken.
  - Show the average time per word.
- The timeout duration is not fixed. You must calculate it dynamically — for example, based on the length of the word.

---

### Report Generator

#### Task Description

This Java project reads and processes multiple order files concurrently using multithreading. Each thread analyzes one file, calculates statistics such as total cost, number of products purchased, average discount, and identifies the most expensive purchase after discount. Product data is preloaded from a shared catalog. The goal is to demonstrate efficient file processing using threads.

#### 🛠 What  You  Need to Do

- Read product data from a Products.txt file and populate a shared product catalog.
- Create a thread (TaskRunnable) for each order file (4 threads) to:
  - Read and parse the file line by line.
  - Accumulate statistics (total cost, total items, average discount, most expensive purchase).
  - **Note that decimal values should be displayed with two decimal places.**
- Use multithreading to process multiple order files in parallel.
- Generate a report for each file, showing:
  - Total cost  (2 decimal points)
  - Total items bought
  - Average discount  (2 decimal points)
  - Most expensive purchase after discount

---

## Bonus Tasks 🌟

- Typing Test
  - User Interface (UI):
    - Create a clean and intuitive UI that displays words to be typed and provides real-time feedback on the user's input.
  - Adaptive Timeout Based on Skill:
    - Measuring Skill: After each word, measure the user's typing speed (words per minute) and accuracy (correct words typed vs. total words typed).
    - Dynamic Timeout: Adjust the timeout for the next word depending on the user's skill.
- Report Generator
  - Data Analysis and Graphs (UI):
    - Sales Over Time: Use line graphs to show how sales have fluctuated over the years

## Evaluation ⚖️

Your work on this assignment will be evaluated based on:

- **Understanding of Multithreading Concepts**: Your ability to accurately answer the theoretical questions, and demonstrating a deep understanding of multithreading in Java. Remember that the answers to the theoretical questions should be provided separately in a markdown file.
- **Code Quality**: Your code should be well-structured, readable, and efficient. Proper use of Java conventions, including variable naming, class structure, and comments, will also be considered.
- You **must not** use advanced multithreading tools (such as ExecutorService, CompletableFuture, or others)
- Total: 100 points

  - 🧠 Theoretical Questions – 30 points
  - 💻 Practical Task 1 (Typing Test) – 35 points
  - 📊 Practical Task 2 (Report Generator) – 35 points
  - 🌟 Bonus Tasks – Up to 10 extra points

## Submission ⌛

1. Add your mentor as a contributor to the project.
2. Create a `develop` branch for implementing features.
3. Use Git for regular code commits.
4. Push your code and the answers file to the remote repository.
5. Submit a pull request to merge the `develop` branch with `main`.

The deadline for submitting your code is **Wednesday, May 7** (17th of Ordibehesht)

## Additional Resources 📚

For assistance with this assignment, you may refer to the following resources:

- [Java Concurrency and Multithreading](https://jenkov.com/tutorials/java-concurrency/index.html)
- [Multithreading Benefits](https://jenkov.com/tutorials/java-concurrency/benefits.html)
- [Concurrency vs. Parallelism](https://jenkov.com/tutorials/java-concurrency/concurrency-vs-parallelism.html)
- [Creating and Starting Java Threads](https://jenkov.com/tutorials/java-concurrency/creating-and-starting-threads.html)

Also, you can find a wealth of knowledge from various YouTube courses. They can be a great source of learning. Alongside, joining discussions on forums and reading helpful documents can also be beneficial.
