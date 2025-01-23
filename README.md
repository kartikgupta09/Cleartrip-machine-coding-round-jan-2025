🎬 ZipReel: Movie Content Management System
ZipReel is a high-performance movie content management system designed for streaming platforms. With multi-level caching (LRU & LFU) and in-memory data handling, it delivers lightning-fast movie searches and efficient user/movie management.

🚀 Features
Movie & User Registration
Register movies with attributes: ID, Title, Genre, Release Year, Rating and users with ID, Name, Preferred Genre.

Multi-Level Caching

L1 Cache: User-specific recent searches (max 5 entries per user, LRU eviction).
L2 Cache: Global popular searches (max 20 entries, LFU eviction).
Primary Store: Complete in-memory movie database.
Search Operations

Search by title, genre, release year, or combinations of filters.
Intelligent search flow: L1 Cache → L2 Cache → Primary Store.
Cache Statistics & Management

View cache hits (L1, L2, Primary Store) and total search counts.
Clear caches (L1 or L2) on demand.
📜 Commands
Add Movie
ADD_MOVIE <id> <title> <genre> <year> <rating>
Example: ADD_MOVIE 1 "Inception" "Sci-Fi" 2010 9.5
Output: Movie 'Inception' added successfully

Register User
ADD_USER <id> <name> <preferred_genre>
Example: ADD_USER 1 "John" "Action"
Output: User 'John' added successfully

Search

By single parameter:
SEARCH <user_id> <search_type> <search_value>
Example: SEARCH 1 GENRE "Action"
By multiple filters:
SEARCH_MULTI <user_id> <genre> <year> <min_rating>
Example: SEARCH_MULTI 1 "Action" 2020 8.0
View Cache Statistics
VIEW_CACHE_STATS
Output:

yaml
Copy
Edit
L1 Cache Hits: X  
L2 Cache Hits: Y  
Primary Store Hits: Z  
Total Searches: N  
Clear Cache
CLEAR_CACHE <cache_level>
Example: CLEAR_CACHE L1
Output: L1 cache cleared successfully

⚙️ System Workflow
Search Flow

Search begins at L1 Cache (user-specific).
If not found, proceeds to L2 Cache (global popular searches).
If still not found, retrieves data from Primary Store.
Cache Hit/Miss

Cache Hit: Data is found in L1 or L2.
Cache Miss: Data is fetched from the primary store and caches are updated accordingly.
🛠️ Design Patterns Used
Factory Pattern: For creating and managing cache levels and primary storage.
Strategy Pattern: To implement eviction policies (LRU for L1, LFU for L2).
Singleton Pattern: Ensures only one instance of each cache exists.
Observer Pattern: Updates global popular searches (L2 cache) based on L1 activity.
Builder Pattern: Simplifies multi-filter search query construction.
🧪 Testing
Unit tests for caching policies, search operations, and edge cases.
Sample test cases are included in the tests directory.


🚦 System Requirements
Language: Java
Storage: In-memory only (no database).
Input/Output: Commands via STDIN or file, results via STDOUT.

