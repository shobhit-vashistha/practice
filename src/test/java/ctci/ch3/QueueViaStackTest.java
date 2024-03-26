package ctci.ch3;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class QueueViaStackTest {

    QueueViaStack.Queue<Integer> queue;

    @BeforeEach
    void setUp() {
        queue = new QueueViaStack.StackQueue<>();
    }

    @Test
    void removeFromEmptyShouldThrowException() {
        assertThrows(NoSuchElementException.class, () -> queue.remove());
    }

    @Test
    void peekFromEmptyShouldThrowException() {
        assertThrows(NoSuchElementException.class, () -> queue.peek());
    }

    @Test
    void testSequence() {
        queue.add(0);
        assertEquals(queue.peek(), 0);
        queue.add(1);
        assertEquals(queue.peek(), 0);
        queue.add(2);
        assertEquals(queue.peek(), 0);
        queue.add(3);
        assertEquals(queue.peek(), 0);
        queue.add(4);

        assertEquals(queue.peek(), 0);
        assertEquals(queue.remove(), 0);

        assertEquals(queue.peek(), 1);
        assertEquals(queue.remove(), 1);

        assertEquals(queue.peek(), 2);
        assertEquals(queue.remove(), 2);

        assertEquals(queue.peek(), 3);
        assertEquals(queue.remove(), 3);

        assertEquals(queue.peek(), 4);
        assertEquals(queue.remove(), 4);
    }


    @Test
    void testAddRemove() {
        queue.add(0);
        queue.add(1);
        assertEquals(queue.remove(), 0);

        queue.add(2);
        assertEquals(queue.remove(), 1);

        queue.add(3);
        queue.add(4);
        assertEquals(queue.remove(), 2);

        assertEquals(queue.remove(), 3);
        assertEquals(queue.remove(), 4);
    }


    @AfterEach
    void tearDown() {
        queue = null;
    }
}