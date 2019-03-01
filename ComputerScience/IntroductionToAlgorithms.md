# Introduction to Algorithms Third Edition

- web site: http://mitpress.mit.edu/algorithms/

# I Foundations

## 1 The Role of Algorithms in Computering
- algorithm
    - input, output
    - computational problem, computational procedure
- sorting problem: sequence of numbers to permutation(reordering)
- instance of a problem = input + computational problem
- correct alogrithm: halts with correct output for every input instance
- incorrect alogrithms with controllable error rate can be useful
- data structure: a way to store and organize data in order to facilitate access and modifications
- techniques of algorithm design and analysis
- different algorithms devised to solve the same problem ofter differ dramatically in their efficiency
- algorithm is a technology

## 2 Getting Started
- insertion sort: an effcient alogrithm for sorting a small number of elements
```cpp
// iter_x: the key to be inserted in [begin, iter_x)
// iter_y: search the position to insert key
template <class Iterator>
void insertion_sort(Iterator begin, Iterator end)
{
    using T = std::iterator_traits<Iterator>::value_type;
    for (Iterator iter_x = begin + 1; iter_x != end; ++iter_x)
    {
        T key = *iter_x;
        Iterator iter_y = iter_x;
        while (iter_y != begin && *(iter_y - 1) > key)
        {
            *iter_y = *(iter_y - 1);
            --iter_y;
        }
        *iter_y = key;
    }
}
```
- loop invariant
- pseudocode conventions
    - indentation indicates block structure
    - short circuit
- analyze an alogrithm: predict resources that the alogirthm requires
- 
# II Sorting and Order Statistics
- sorting problem
- record, key, satellite data
- permute an array of pointers to the records rather than the records themselves in order to minimize data movement
- comparison sort algorithms
    - insertion sort
    - merge sort
    - heapsort
    - quicksort
- counting sort
- radix sort
- bucket sort
- order statistics: the ith order statistic of a set of n numbers is the ith smallest number in the set, O(n)

## 6 Heapsort
- running time: O(n lg n)
- in-place: only a constant number of array elements are stored outside the input array at any time
- algorithm design technique: using a data structure to manage information
- heap: heapsort, priority queue
- heap data structure != heap garbage-collected storage
- binary heap: nearly complete binary tree
    - completely filled on all levels except possibly the lowest, which is filled from the left up to a point
    - represented as array A
        - A.length: the number of elements in array A
        - A.heap_size: the number of heap elements in array A
        - 0 <= A.heap_size <= A.length
    - root: array[0]
    - node: array[i]
        - parent: array[(i - 1) / 2], (--i) >> 1
        - left-child: array[2 * i + 1], ++(i << 2)
        - right-child array[2 * i + 2], (++i) << 1
- heap property
    - max-heap: parant node key >= child node key ==> root key >= other nodes' keys
    - min-heap: parend node key <= child node key ==> root key <= other nodes' keys
- heap procedures time cost
    - max-heapify: O(lgn)
    - build-max-heap: O(1)
    - heap-sort: (nlgn)
    - max-heap-insert, heap-extract-max-heap-increase-key, heap-maximum: O(lgn)
```cpp
size_t parent(size_t i) {return (--i) >> 1};
size_t left(size_t i) {return ++(i << 1)};
size_t right(size_t i) {return (++i) << 1};

// maintain the max-heap property
// binary trees rooted at left(i) and right(i) are max-heaps
// a[i] might be smaller than left(i) or right(i)
// O(lgn)
template <typename T>
void max_heapify(std::vector<T>& heap, size_t i)
{
    size_t l = left(i);
    size_t r = right(i);
    size_t largest = i;
    if (l < heap.size() && heap[l] > heap[largest]) largest = l;
    if (r < heap.size() && heap[r] > heap[largest]) largest = r;
    if (largest != i)
    {
        std::swap(heap[i], heap[largest]);
        max_heapify(heap, largest);
    }
}

// leaf nodes: (n+1)/2, ... ,n - 1
// not O(nlgn) but O(n)
template <typename T>
void build_max_heap(std::vector<T>& heap)
{
    for (size_t pos = (heap.size() - 1) / 2; pos >= 0; --pos)
    {
        max_heapify(heap, pos);
    }
}

// O(nlgn)
template <typename T>
void heapsort(std::vector<T>& heap)
{
    const size_t size = heap.size();
    build_max_heap(heap);   // O(n)
    for (size_t pos = heap.size() - 1; pos > 0; --pos)
    {
        std::swap(heap[0], heap[pos]);
        heap.pop_back();
        max_heapify(heap, 0);   // O(lgn)
    }
    heap.resize(size);
}
```

- priority queue
    - max-priority queue
    - min-priority queue
- priority queue: a data structure for maintaining a set of S of elements, each with an associated value called a key
- max-priority queue operations
    - insert(s, x)
    - maximun(s)
    - extract_max(s)
    - incerease_key(s, x, k)
- use max-priority queues to schedule jobs on a shared computer
    - select the highest-priority job: extract_max
    - add a new job: insert
- min-priority queue operations: insert, minimun, extract_min, decrease_key
- use min-priority queues in an exent-driven simulator
    - item: events to be simulated
    - key: time of occurrence
    - events must be simulated in order of time of occurrence
    - simulate nexe event: extract_min
    - new event is produced: insert
```cpp
template <typename T>
T heap_maximum(std::vector<T>& heap) { return heap[0];}

// O(lgn)
template <typename T>
T heap_extract_max(std::vector<T>& heap)
{
    T max = heap[0];
    heap[0] = heap[heap.size() - 1];
    heap.pop_back();
    max_heapify(heap, 0);
    return max;
}

// O(lgn)
template <typename T>
void heap_increase_key(std::vector<T>& heap, size_t pos, T key)
{
    assert(key >= heap[pos]);
    heap[pos] = key;
    while (pos > 0 && heap[parent(pos)] < heap[pos])
    {
        std::swap(heap[pos], heap[parent(pos)]);
        pos = parent(pos);
    }
}

// O(lgn)
template <typename T>
void heap_decrease_key(std::vector<T>& heap, size_t pos, T key)
{
    assert(key <= heap[pos]);
    heap[pos] = key;
    max_heapify(heap, pos);
}

// O(lgn)
template <typename T>
T max_heap_insert(std::vector<T>& heap, T key)
{
    heap.push_back(key);
    heap_increase_key(heap, heap.size() - 1, key);
}

template <typename T>
build_max_heap_using_insertion(std::vector<T>& heap)
{
    const size_t size = heap.size();
    heap.resize(1);
    for (size_t pos = 1; pos < size; ++pos)
    {
        max_heap_insert(heap, heap[pos]);
    }
}
```
- d -ary heap

## 7 Quicksort
- running time on an array of size n
    - worst-cast: Θ(n ^ 2)
    - expected/average: Θ(nlgn) with quite small constant factors
    - the best practical choice for sorting
- other advantanges
    - in-place
    - works well even in virtual-memory environments

```cpp
// *(end - 1): pivot
// [begin, iter_pivot): < pivot, partition1
// [iter_pivot, iter): >= pivot, partition2
// [iter, end - 1): unrestricted
// Θ(n), n = end - begin
template <typename Iterator>
Iterator partition(Iterator begin, Iterator end)
{
    using T = std::iterator_traits<Iterator>::value_type;
    Iterator iter = begin;
    Iterator iter_pivot = begin;
    while (iter < end - 1)
    {
        if (*iter < *(end - 1))
        {
            std::swap(*iter, *iter_pivot);
            ++iter_pivot;
            ++iter;
        }
        else ++iter;
    }
    std::swap(*iter_pivot, *(end - 1));
    return iter_pivot;
}

template <typename Iterator>
void quicksort(Iterator begin, Iterator end)
{
    if (begin == end) return;
    Iterator iter = partition(begin, end);
    quicksort(begin, iter);
    quicksort(iter + 1, end);
}
```

- running time of quicksort depends on whether the partitioning is balanced or unbalanced, which in turn depends on which elements are used for partitioning
- worst-case partitioning
- best-case partitioning
- balanced partitioning
- a randomized version of quicksort
    - add randomization to an algorithm in order to obtain good expected performance over all inputs
- random sampling
```cpp
template <typename Iterator>
Iterator randomized_partition(Iterator begin, Iterator end)
{
    Iterator pos = random(begin, end);
    std::swap(*pos, *(end - 1));
    return partition(begin, end);
}

template <typename Iterator>
void randomized_quicksort(Iterator begin, Iterator end)
{
    if (begin == end) return;
    Iterator iter = randomized_partition(begin, end);
    randomized_quicksort(begin, iter);
    randomized_quicksort(iter + 1, end);
}
```

- analysis of quicksort
    - worst-case analysis
    - expected running time


## 8 Sorting in Linear Time

- comparison sorts: the sorted order is based only on comparisons between the input elements
- any comparison sort must make Ω(nlgn) comparisons in the worst case to sort n elements
- counting sort, radix sort, bucket sort: O(1) time
- lower bounds for sorting
- counting sort
    - input elements are integers in [0, k), for some integer k
    - when k = O(n), counting sort runs in Θ(n) time
    - stable: numbers with the same value remain their order

```cpp
// array contains integers in [0, bound)
std::vector<int> counting_sort(const std::vector<int>& array, int bound)
{
    assert((*std::min_element(array.begin(), array.end()) >= 0))
    assert((*std::max_element(array.begin(), array.end()) < bound))
    
    std::vector<int> sorted(array.size(), 0);
    std::vector<int> counts(bound, 0);
    for (const auto& value : array) ++counts[value];
    for (size_t pos = 1; pos < bound; ++pos) counts[pos] += counts[pos - 1];
    for (size_t pos = array.size() - 1; pos >= 0; --pos) sorted[--counts[array[pos]]] = array[pos];
    return sorted;
}
```

- radix sort
    - used by card-sorting machines
- bucket sort
    - the input is drawn from a uniform distribution over [0, 1)
    - average-case running time O(n)
    - divides the interval [0, 1) into n equal-sized subintervals, or buckets

```cpp
void bucket_sort(std::vector<double>& array)
{
    const size_t size = array.size();
    std::vector<std::list<double>> buckets(size);
    for (auto& value : array) buckets[size * value].push_back(value);
    for (auto& list : buckets) list.sort();
    size_t pos = 0;
    for (auto& list : buckets)
    {
        for (auto& value : list)
        {
            array[pos] = value;
            ++pos;
        }
    }
}
```



# III Data Structures


# VI Advanced Design and Analysis Techniques
- 3 important techinques used in designing and andlyzing efficient Algorithms
    - dynamic programming
        - optimization problems: make a set of choices in order to arrive at an optimal solution
        - same subproblems in different choices
        - store solution of subproblem in case it would reappear
    - greedy algorithms
        - optimization problems
        - make each choice in a locally optimal manner
        - matroid theory: provides a mathematical basis to show that a greedy algorithm yields an optimal solution
    - amortized analysis
        - analyze certain algorithms that perform a sequence of similar operations
        - not just an analysis tool, but also a way of thinking about the design of algorithms

## 15 Dynamic Programming
- divide-and-conquer: solves problems by combining the solutions to subproblems
- dynamic programming: solves problems by combining the solution to subproblems and solving each subsubproblem only once
- dynamic programming = divide-and-conquer + overlapping subsubproblems
- optimization problems
    - such problems can have many possible solutions ==> find optimal solution
    - get an/the optimal solution or the value of optimal solution
    - an optimal solution
    - the optimal solution: one or more optimal solutions
- 4 steps whem developing a dynamic-programming algorithm
    - characterize the structure of an optimal solution
    - recursively define the value of an optimal solution
    - compute the value of an optimal solution, typically in a bottom-up fashion
    - construct an optimal solution from computed information


# V Advanced Data Structures
- B-tree
    - balanced search trees specifically designed to be stored on disks
    - disks operate much more slowly than random-access memory
    - measure the performance of B-trees not only by how much computing time the dynamic-set operations consume but also by how many disk accessed they perform
    - the number of disk accessed increases with the height of the B-tree, but B-tree operations keep the height low
- mergeable heap
    - supports the operations insert, minimun/maxinum, extract-min.max and union
    - mergeable min-heap, mergeable max-heap
- fibonacci heap
    - supports the operations delete and decrease-key
    - the operations insert, minimun, and union take O(1) actual and amortized time
    - the operations extract-min and delete take O(lg n) amortized time
    - advantage: decrese-key takes only O(1) amortized time
    - key components of some of the asymptotically fastest algorithms to date for graph problems
- van Emde Boas tree: O(lg lg u)
- disjoint set
- dynamic tree
- splay tree
- persistent
- fusion tree
- exponential search tree
- dynamic graph data structure
# VI Graph Algorithms


# VII Seleted Topics

# VIII Appendix: Mathematical Background
