package ctci.ch3;

import java.util.LinkedList;
import java.util.NoSuchElementException;


/**
 * Animal Shelter: An animal shelter, which holds only dogs and cats, operates on a strictly "first in, first
 * out" basis. People must adopt either the "oldest" (based on arrival time) of all animals at the shelter,
 * or they can select whether they would prefer a dog or a cat (and will receive the oldest animal of
 * that type). They cannot select which specific animal they would like. Create the data structures to
 * maintain this system and implement operations such as enqueue, dequeueAny, dequeueDog,
 * and dequeueCat.You may use the built-in LinkedList data structure.
 */
public class AnimalShelter {

    interface Animal {}
    public static class Cat implements Animal {}
    public static class Dog implements Animal {}

    interface AnimalQueue {
        void enqueue(Animal animal);
        Animal dequeueAny();
        Cat dequeueCat();
        Dog dequeueDog();
    }


    public static class SimpleAnimalQueue implements AnimalQueue {

        private static class AnimalEntry {
            Animal animal;
            long id;

            public AnimalEntry(Animal animal, long id) {
                this.animal = animal;
                this.id = id;
            }
        }

        private final LinkedList<AnimalEntry> cats;
        private final LinkedList<AnimalEntry> dogs;
        private long nextId = 0;

        public SimpleAnimalQueue() {
            cats = new LinkedList<>();
            dogs = new LinkedList<>();
        }

        @Override
        public void enqueue(Animal animal) {
            if (animal instanceof Cat) {
                cats.addLast(new AnimalEntry(animal, nextId));
                nextId++;
            } else if (animal instanceof Dog) {
                dogs.addLast(new AnimalEntry(animal, nextId));
                nextId++;
            }
        }

        @Override
        public Animal dequeueAny() {
            if (cats.isEmpty() && dogs.isEmpty()) {
                throw new NoSuchElementException();
            } else if (cats.isEmpty()) {
                return dogs.pop().animal;
            } else if (dogs.isEmpty()) {
                return cats.pop().animal;
            } else {
                return cats.peek().id < dogs.peek().id ? cats.pop().animal : dogs.pop().animal;
            }
        }

        @Override
        public Cat dequeueCat() {
            if (cats.isEmpty()) throw new NoSuchElementException();
            return (Cat) cats.pop().animal;
        }

        @Override
        public Dog dequeueDog() {
            return (Dog) dogs.pop().animal;
        }
    }
}
