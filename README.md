ZipReel: Movie Content Management System
ZipReel is a movie content management system designed for a streaming platform. It efficiently handles frequent user searches and provides lightning-fast responses using a multi-level caching mechanism.

Features
Core Functionality
Movie and User Registration

Add movies with attributes: ID, Title, Genre, Release Year, Rating.
Register users with attributes: ID, Name, Preferred Genre.
Multi-level Caching for Optimized Searches

L1 Cache: User-specific cache for recent searches (max 5 entries per user, LRU eviction).
L2 Cache: Global cache for popular searches (max 20 entries, LFU eviction).
Primary Store: Complete movie database stored in-memory.
Search Operations

Search by single parameter: title, genre, or release year.
Search with multiple filters: genre, release year, and minimum rating.
Search flow: L1 Cache → L2 Cache → Primary Store.
Cache Statistics and Management

View cache hits and total searches for each cache level.
Clear specific cache levels (L1 or L2).
Bonus Features
Intelligent cache update mechanism based on search results.
In-memory data structure for all operations (no database usage).
Multi-Level Cache Explanation
L1 Cache: Stores user-specific recent searches (up to 5 entries per user) and evicts entries using the Least Recently Used (LRU) policy.
L2 Cache: Maintains global popular searches (up to 20 entries) and evicts entries using the Least Frequently Used (LFU) policy.
Primary Store: The main in-memory movie database.
Cache Flow:

Cache Hit: Data is found in L1 or L2 cache.
Cache Miss: Data is fetched from the primary store and added to the caches.
Commands
1. Add Movie
bash
Copy
Edit
ADD_MOVIE <id> <title> <genre> <year> <rating>
Example: ADD_MOVIE 1 "Inception" "Sci-Fi" 2010 9.5
Output: Movie 'Inception' added successfully
2. Register User
bash
Copy
Edit
ADD_USER <id> <name> <preferred_genre>
Example: ADD_USER 1 "John" "Action"
Output: User 'John' added successfully
3. Search Operations
Search by a single parameter:
bash
Copy
Edit
SEARCH <user_id> <search_type> <search_value>
Example: SEARCH 1 GENRE "Action"
Output: [List of movies with cache level indicator]
Search with multiple filters:
bash
Copy
Edit
SEARCH_MULTI <user_id> <genre> <year> <min_rating>
Example: SEARCH_MULTI 1 "Action" 2020 8.0
Output: [List of filtered movies with cache level indicator]
4. View Cache Statistics
bash
Copy
Edit
VIEW_CACHE_STATS
Output:
L1 Cache Hits: X
L2 Cache Hits: Y
Primary Store Hits: Z
Total Searches: N
5. Clear Cache
bash
Copy
Edit
CLEAR_CACHE <cache_level>
Example: CLEAR_CACHE L1
Output: L1 cache cleared successfully
