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

## 10 Elementary Data Structures
- the representation of dynamic sets by simple data structures that use pointers
- stack, queue, linked list, and rooted tree
### 10.1 Stacks and queues
- stack: LIFO, last-in, first-out, push/pop/top
- queue: FIFO, first-in, first-out, push/pop/front/back/(enqueue/dequeue)
```cpp
// std::deque, std::vector, std::list
template<typename T, typename Container>
class stack
{
public:
    T& top() { return m_container.back(); }
    void pop() { m_container.pop_back(); }
    void push(const T& value) { m_container.push_back(value); }
private:
    Container<T> m_container;
}

// std::deque, std::list
template<typename T, typename Container>
class queue
{
public:
    T& front() { return m_container.front(); }
    T& back() { return m_container.back(); }
    void pop() { m_container.pop_front(); }
    void push(const T& value) { m_container.push_back(value); }
private:
    Container<T> m_container;
}
```

### 10.2 Linked lists
- singly linked list: key, next
- doubly linked list: key, prev, next
- sorted list
- circular list
- head, tail
- sentinel: turn list into circular
    - sentinel->next = head;
    - tail->next = sentinel
```cpp
template <typename T>
struct ListNode
{
    T value;
    ListNode* next;
    ListNode(T val = 0, ListNode* nex = nullptr)
        : value(val), next(nex) {}
}

template <typename T>
ListNode* list_search(ListNode* node, T value)
{
    while (node && node->value != value) node = node->next;
    return node;
}

template <typename T>
void list_insert(ListNode* node, T value)
{
    ListNode* tmp = new ListNode(value, node->next);
    node->next = tmp;
}

template <typename T>
void list_delete(ListNode* node) 
{
    if (node->next)
    {
        ListNode* tmp = node_next;
        node->next = tmp->next;
        delete tmp;
    }
}
```

### 10.3 Implementing pointers and objects
- implement pointers and objects in languages that do not provite them
- implement linked data structures without an explicit pointer data type
- a multiple-array representation of objects
    - three arrays: array key, array prev, array next
    - key[index], prev[index], next[index] represent an object in the linked list
    - array prev/next contains the index of prev/next object
- a single-array representation of objects
    - an object occupies a contiguous set of locations
    - a pointer is the address of the first memory location of object
    - permit objects of different lengths to be stored in the same array
- garbage collector: responsible for determining which objects are unused
    - free list
    - several linked lists with just a single free list
```cpp
template<typename T>
struct ListNode
{
    T value;
    int next;
};

template <typename T, typename size>
class ListAllocator
{
public:
    ListAllocator()
    {
        m_free = 0;
        for (int index = 0; index < size - 1; ++index)
        {
            m_array[index].next = index + 1;
        }
        m_array[size - 1].next = -1;
    }
    
    int allocate_object()
    {
        if (m_free < 0) return -1;
        int obj = m_free;
        m_free = m_array[obj].next;
        obj.next = -1;
        return obj;
    }
    
    void free_object(int obj)
    {
        m_array[obj].next = m_free;
        m_free = obj;
    }
    
    ListNode& access_object(int obj)
    {
        return m_array[obj];
    }
private:
    int m_free;
    std::array<ListNode<T>, size> m_array;
};
```

### 10.4 Representing rooted trees
- binary trees: left child, right child
- rooted trees with unbounded branching: left-child, right-sibling representation
- other tree representations: heap; no points to children


## 11 Hash Tables
- dictionary operations: insert, search, delete
- the hash table is the generalization of the array
- key ==> index ==> value, collision ==> chain

### 11.1 Direct-address tables
- slot
### 11.2 Hash tables
- the set K of keys stored in a dictionary is much smaller than the universe U of all possible keys
- hash function: U -> {0, 1, ..., m - 1}
- hash value
- slot
- size m of hash table is much typically much less than |U|
- collision
    - minimize the number of collisions: choose a suitable hash function
    - resolve collisions: chaining, open addressing
- chaining: place all elements that hash to the same slot into the same linked list
- simple uniform hashing
- time 
    - insert, delete: O(1)
    - search: O(1)~Θ(n)
```cpp
template <typename Key>
size_t hash_function(const Key& key);

template<typename Key, typename Val>
class ChainHashTable
{
public:
    void insert(Key key, Val val) 
    {
        m_table[hash_function(key)].emplace_front(key, val);
    }
    void delete(Key key, Val val)
    {
        m_table[hash_function(key)].delete(key, val);
    }
private:
    std::vector<std::list<std::pair<Key, Val>>> m_table;
}
```

### 11.3 Hash functions
- three schemes
    - hashing by division
    - hashing by multiplication
    - universal hashing

## 12 Binary Search Trees
- operations: search, minimun, maximun, predecessor, successor, insert, delete
- a binary search tree can be used as a dictionary or a priority queue
```cpp
template <class Key, class Value>
struct BinarySearchTreeNode {
    Key key;
    Value value;
    BinarySrearchTreeNode* left;
    BinarySrearchTreeNode* right;
};
// binary-search-tree property: node->left->key <= node->key <= node->right->key
```
## 13 Red-Black Trees
- 

## 14 Augmenting Data Structures
- augmented red-black tree
    - order-statistic tree
### Dynamic order statistics
- order-statistic tree
    - any order statistics in O(lg n) time from an unordered set based on red-black tree
    - enable equal keys

```cpp
enum class Color { RED, BLACK };
template <class Key>
struct OrderStatisticTreeNode {
    Key key;
    Color color;
    OrderStatisticTreeNode* parent;
    OrderStatisticTreeNode* left;
    OrderStatisticTreeNode* right;
    size_t size;    // the number of nodes
};
// node->size = node->left->size + node->right->size + 1
```
### Interval trees
```cpp
emum class Color { RED, BLACK };
template <class Value>
struct Interval {
    Value low;  // low endpoint
    Value high; // high endpoint
};
bool operator< (const Interval& left, const Interval& right) {
    return left.low < right.low;
}
template <class Key, class Value>
struct IntervalTreeNode {
    Key key;
    Color color;
    IntervalTreeNode* parent;
    IntervalTreeNode* left;
    IntervalTreeNode* right;
    Interval<Value> interval;   // the interval itself
    Value max;  // the maximum interval endpoint value in the subtree rooted at this node
};
// node->max = max(node->interval.high, node->left->max, node->right->max)
```

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
- dynamic programming: solves problems by combining the solution to subproblems and solving each subsubproblem only once by memorization
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
### Rod cutting
- rod-cutting problem: given a rod of length n inches and a table of prices p_i for i = 1, 2, ..., n, determine the maximum revenue r_n obtainable by cutting up the rod and selling the pieces
- optimal substructure: optimal solutions to a problem incorporate optimal solutions to related subproblems, which we may solve independently
- induction
- two equivalent ways to implement a dynamic-programming approach
    - top-down with memorization(uaually array or hash table)
    - bottom-up method(sort subproblems by size and solve them in size order, smallest first)
- subproblem graphs: the set of subproblems involved and how subproblems depend on one another
    - bottom-up dynamic-programming algorithm: reverse topological sort/topoligical sort of the transpose
    - up-down dynamic-programming algorithm: depth-first search
```cpp
// recursive top-down implementation
// O(2^n), exponential time
// solves the same subproblems repeately
template <typename T>
T cut_rod(std::vector<T>& prices, unsigned length) {
    if (length == 0) return 0;
    int ret = INT_MIN;
    for (unsigned i = 0; i <= length; ++i) {
        ret = std::max(ret, p[i] + cut_rod(prices, length - i));
    }
    return ret;
}

// dynamic programming
// each subproblem is solved only once
// time-memory trade-off
// Θ(n ^ 2), polynomial time
template <typename T> 
T memorized_cut_rod(std::vector<T>& prices, unsigned length) {
    std::vector<T> cache(length + 1, std::numeric_limits<T>::lowest());
    return memorized_cut_rod_aux(prices, length, cache);
}

template <typename T>
T memorized_cut_rod_aux(std::vector<T>& prices, unsigned length, std::vector<T>& cache) {
    T ret = cache[length];
    if (ret != std::numeric_limits<T>::lowest()) return ret;
    if (n == 0) ret = 0;
    else {
        for (usigned i = 0; i <= length; ++i) {
            ret = std::max(ret, p[i] + memorized_cut_rod_aux(p, length - i, cache));
        }
    }
    cache[n] = ret;
    return ret;
}

template <typename T>
T bottom_up_cut_rod(std::vector<T>& prices, unsigned length) {
    std::vector<T> cache(length + 1, std::numeric_limits<T>::lowest());
    cache[0] = 0;
    for (unsigned i = 0; i <= length; ++i) {
        for (unsigned j = 0; j <= i; ++j) {
            cache[j] = std::max(cache[j], prices[i] + cache[i - j]);
        }
    }
    return cache[length];
}

// return: for each subproblem, the maximum revenue and the optimal size of the first piece to cut
template <typename T>
std::vector<std::tuple<T, int>> 
extended_bottom_up_cut_rod(std::vector<T>& prices, unsigned length) {
    std::vector<std::tuple<T, int>> answers(length + 1, {std::numeric_limits<T>::lowest(), 0});
    answers[0].first = 0;
    for (int i = 0; i <= length; ++i) { // whole size
        for (int j = 0; j <= i; ++j) {  // cut-off size
            T revenue = prices[j] + answers[i - j].first;
            if (answers[i].first < revenue) {
                answers[i].first = revenue;
                answers[i].second = j;
            }
        }
    }
    return answers;
}

template <typename T>
std::vector<int> get_cut_rod_solution(std::vector<T>& prices, int n) {
    std::vector<std::tuple<T, int>> answers = extended_bottom_up_cut_rod(p, n);
    std::vector<int> ret;
    while (n > 0) {
        ret.push_back(answers[n].second);
        n -= answers[n].second;
    }
    return ret;
}
```

### Matrix-chain multiplication
- compute the product of n matrics A1, A2, ..., An
- matrix-chain multiplication problem: given a chain <A1, A2, ... An> of n matrics, where for i = 1, 2, ..., n, matrix Ai has dimension p_(i-1) * p_i, fully parenthesize the product A1A2...An in a way that minimizes the number of scalar multiplications
- 
```cpp
template <typename T> class Matrix;

// A: p*q, B: q*r, C = AB: p*r
// time: O(pqr)
template <typename T> 
Matrix<T> matrix_multiply(Matrix<T>& A, Matrix<T>& B) {
    if (A.cols != B.rows) throw "imcompatible dimensions";
    Matrix<T> ret(A.rows, B.cols, 0);   // row num, col num, init value
    for (int row = 0; row < ret.rows; ++row) {
        for (int col = 0; col < ret.cols; ++col) {
            for (int i = 0; i < A.cols; ++i) {
                ret.at(row, col) += A.at(row, i) * A.at(i, col);
            }
        }
    }
    return ret;
}
```
## 16 Greedy Algorithms
- make a locally optimal choice in the hope that this choice will lead to a globally optimal solution
- do not always yield optimal solution

### activity-selection problem
- problem: schedule several competing activities that require exclusive use of a common resource
    - goal: select a maximun-size set of mutually compatible activities
    - each activity has start time and finish time: 0 <= s < f, half-open internal [s, f)
    - compatible: intervals not overlap


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

## 18 B-Trees
- B-Trees are balanced search trees designed to work well on disks or other direct-access secondary storage devices
- amortize the time spent waiting for mechanical movements
    - access ont just one item but several at a time: pages
- a B-tree node is usually as large as a whole disk page, and the size limits the number of children a B-tree node can have
- branching factors are usually 50~2000, depending on the size of a key relative to the size of page
- B-tree properities
    - internal node: N nodecreasing keys, N + 1 children
    - leaf node: no keys, no children
    - keys separate the ranges of keys stored in each substree
    - all leaves same depth -> B-tree height
    - minimun degree t
        - every node have at least t - 1 keys and t children
        - every node have at most 2t - 1 keys and 2t chidren
        - B-tree requires half-full
        - simplest B-tree: t = 2, every internal node have either 2, 3, or 4 children, called 2-3-4 tree


# VI Graph Algorithms
- G = (V, E)
    - G: graph
    - V: vertex
    - E: edge
- directed/undirected graphs
- weighted graphs, weight function

## 22 Elementary Graph Algorithms
### representations
- adjacency-list representation
    - an array of |V| lists for each vertices
    - a compact way to represent sparse graphs
    - |E| is much less than |V|^2
- adajcency-matrix representation
    - dense: |E| is close to |V|^2
    - quickly get the relationship of two given vertices
- representing attributes
    - attributes for vertices and/or edges
    - no one best way to store and access vertex ans edge attributes

### breadth-first search
- breadth-first tree
- shortest path between two vertices
- vertex states
    - discovered
    - discovering(frontier between discovered and undiscovered)
    - undiscovered

### depth-first search
- the archetype for many important graph algorithms
    - Prim's minimun-spanning-tree algorithm
    - Dijkstra's single-source shortest-paths algorithm
- results
    - the distance from source vertex to another vertex
    - breadth-first tree with root source vertex containing all reachable vertices
- backtrack
- vertex colors: white, gray, black
    - white: undiscovered vertices
    - gray: the frontier between discovered and undiscovered vertices
    - black: discovered vertices
```c
// time complexity: O(V + E)
// scan and enqueue/dequeue each vertex once: O(V)
// scan the adjacency list of each vertex once: O(E)
BFS(G, s)
    for each vertex u in G.V - {s}
        u.color = WHITE
        u.distance = -1
        u.predecessor = NIL
    s.color = GRAY
    s.distance = 0
    s.predecessor = NIL
    Queue Q
    Enqueue(Q, s)
    while Q != 0
        u = Dequeue(Q)
        for each v in G.Adj[u]
            if v.color == WHITE
                v.color = GRAY
                v.distance = u.distance + 1
                v.predecessor = u
                ENQUEUE(Q, v)
        u.color = BLACK
```
#### shortest path
- shortest-path distance: δ(s, v)
- G = (V, E), vertex s in V, edge (u, v) in E ==> δ(s, v) <= δ(s, u) + 1

#### breadth-first tree
- predecessor subgraph $G_\pi = (V_\pi, E_\pi)$
- $ |E_\pi| = |V_\pi| - 1$

### depth-first search
- predecessor subgraph
- depth-first forest, depth-first tree
- vertex states
    - white: undiscovered
    - gray: discovered
    - black: finished, its adjacency list has been examined completely
- timestamp(each vertex has two timestamps)
    - first discovered: white -> gray
    - finish and backtrack: gray -> black
```c
// Θ(E + V)
DFS(G)
    for each vertex u in G.V
        u.color = WHITE
        u.π = NIL   // predecessor
    time = 0
    for each vertex u in G.V
        if u.color == WHITE
            DFS-Viset(G, u)

DFS-Visit(G, u)
    time = time + 1
    u.d = time  // discovery time
    u.color = GRAY
    for each v in G.Adj[u]
        if v.color == WHITE
            v.π = u
            DFS-Visit(G, v)
    u.color = BLACK
    time = time + 1 
    u.f = time  // finishing time
```
- properties of depth-first tree
    - valuable information about the structrue of a graph
    - predecessor subgraph
    - discovery and finishing times have parenthesis structure
        - discover vertex u: (u
        - finish vertex u : u)
        - parenthesis expression
- parenthesis theorem
    - intervals [u.d, u.f] and [v.d, v.f] are entirely disjoint, and neither u nor v is a descendant of the other in the depth-first tree
    - interval [u.d, u.f] is contained entirely within interval [v.d, v.f], and u is a descendant of v in a depth-first tree, vice versa
- nesting of descendants' intervals
    - vertex v is a proper descendant of vertex u in the depth-first forest for a (directed and undirected) graph G if and only if u.d < v.d < v.f < u.f
- white-path theorem
    - in a depth-first forest of a (directed or undirected) graph G, vertex v is a descendant of vertex u if and only if a the time u.d that search discovers u, there is a path from u to v consisting entirely of white vertices
- classification of edges
    - tree edges
    - back edges
    - forward edges
    - corss edges


### topological sort
- directed acyclic G = (V, E)
- dag: directed acyclic graph
- topological sort of a dag G = (V, E): linear ordering of all vertices such that if G containes an edge (u, v), then u apprears before v in the ordering 
```c
TOPOLOGICAL-SORT(G)
    call DFS(G) to compute finishing times v.f for each vertex v
    as each vertex is finished, insert it onto to front of a linked list
    return the linked list of vertices
```
- a directed graph G is acyclic if and only if a depth-first search of G yields no back edges

### strongly connected components
- the transpose of G = (V, E): $ G^T = (V, E^T), where \ E^T = \{ (u, v):(v, u) \in E \} $
- $G$ and $G^T$ have the same strongly connected components
```c
STRONGLY-CONNECTED-COMPENENTS(G)
    call DFS(G) to compute finishing times u.f for each vertex u
    compute G^T
    call DFS(G^T), but in the main loop of DFS, consider the vertices
        in order of descreasing u.f
    output the vertices of each tree in the depth-first forest 
        as a separate string connected component
```

## 23 Minimun Spanning Trees
- minimun-spanning-tree problem
    - connected, undirected graph G = (V, E)
    - each edge (u, v) has a weight w(u, v)
    - spanning tree: acyclic subset $T = (V, E^T)$
    - total weight $ w = \sum \limits_{(u, v) \in T} w(u, v)  $
- greedy algorithms to slove minimum-spanning-tree problem
    - Kruskal's algorithm: O(ElgV)
    - Prim's algorithm: O(E + VlgV)
        - using Fibonacci heaps
        - better when |V| is much smaller than |E|

#### growing a minimun spanning tree

#### the algorithms of Kruskal and Prim

### 24 Single-Source Shortest Paths

### 25 All-Pairs Shortest Paths

### 26 Maximun Flow

# VII Seleted Topics

# VIII Appendix: Mathematical Background
