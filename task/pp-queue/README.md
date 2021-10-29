## Параллельное программирование

### Блокирующая очередь

**Блокирующие очереди** используются в тех случаях, когда нужно выполнить (проверить выполнение)
какие-либо условия для продолжения потоками своей работы. 
Блокирующие очереди могут реализовывать интерфейсы 
[BlockingQueue](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/BlockingQueue.html), 
[BlockingDeque](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/BlockingDeque.html),
[TransferQueue](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/TransferQueue.html). 
В пакете [java.util.concurrent](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/package-summary.html)
имеются следующие реализации блокирующих очередей:

* **ArrayBlockingQueue** — очередь, реализующая классический кольцевой буфер;
* **LinkedBlockingQueue** — односторонняя очередь на связанных узлах;
* **LinkedBlockingDeque** — двунаправленная очередь на связанных узлах;
* **SynchronousQueue** — блокирующую очередь без емкости (операция добавления одного потока находится в ожидании соответствующей операции удаления в другом потоке);
* **LinkedTransferQueue** — реализация очереди на основе интерфейса TransferQueue;
* **DelayQueue** — неограниченная блокирующая очередь, реализующая интерфейс Delayed;
* **PriorityBlockingQueue** — реализация очереди на основе интерфейса PriorityQueue.

### Задание №1

В классе [PPQueue](src/main/java/ru/bgpu/ppqueue/PPQueue.java) произвести реализацию интерфейса
[BlockingQueue](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/BlockingQueue.html) с
использованием одномерного массива и низкоуровневых потоков Java.


### Задание №2

Доработать клиентский код так, чтобы проводилась проверка методов:
* add(E e)
* contains(Object o)
* offer(E e, long timeout, TimeUnit unit)
* poll(long timeout, TimeUnit unit)
* remove(Object o)
