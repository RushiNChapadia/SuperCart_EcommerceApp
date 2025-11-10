🛒 EcommerceApp

A modern and fully functional Android Ecommerce Application built using Java, XML, and Android Studio.
The app enables users to browse products, view categories, explore details, and make selections within a clean and responsive UI.

📱 Screenshots
Dashboard	Categories	Product Details	Cart



<img width="150" height="450" alt="c7b7c85b6a5453097ebf266ad1be258" src="https://github.com/user-attachments/assets/f822416f-9441-4531-ad83-7caf35d9fa6a" />

<img width="150" height="450" alt="c201ccc66ca748f5a9edc68b0abc1665" src="https://github.com/user-attachments/assets/b5619c7e-4215-49a1-934f-91cbea476ea4" />

<img width="150" height="450" alt="794a4042188e4f0e8a5caf853899904d" src="https://github.com/user-attachments/assets/7343ca6a-163a-4c9a-a8f0-20bdf80bdf26" />

<img width="150" height="450" alt="4249c51c8dc640f499c1b710c2327024" src="https://github.com/user-attachments/assets/435b633e-0a82-4bcb-b417-0f3e43ef995a" />

<img width="150" height="450" alt="84360dc70c1447b0a8f5c19b8d3171db" src="https://github.com/user-attachments/assets/7602956c-3ed0-4665-87d1-f1cff1fb3836" />

<img width="150" height="450" alt="1f719d9c2d6e42ab8900fabcacaabd8b" src="https://github.com/user-attachments/assets/dc3d27fb-7509-4031-a7fc-a3631ef9f5f3" />

<img width="150" height="450" alt="6173860f03b474b966a6e00104c950f" src="https://github.com/user-attachments/assets/3f1b3a5c-8434-4a41-b7d2-dc0bc1fbf978" />

<img width="150" height="450" alt="8bb4bc5306d84c54ba9b4d6e9c641e3d" src="https://github.com/user-attachments/assets/7af3998c-c1f6-4934-bc6c-9591fa7dae6a" />

<img width="150" height="450" alt="37cb1d224f5c4de1a0befce5444d2631" src="https://github.com/user-attachments/assets/b1b9e8db-791f-40bd-829d-69300bd93c54" />

<img width="150" height="450" alt="666c5f90ca964866baf4a395bb9e1b42" src="https://github.com/user-attachments/assets/261f20b6-6358-4475-9010-cb98f6f8e289" />

<img width="150" height="450" alt="9ca98ac75897491b8e77348535cce633" src="https://github.com/user-attachments/assets/5066c46a-d243-4591-9326-b0d68d93eb50" />

<img width="150" height="450" alt="79bf482d97ba4124a5e725e7d0aab35f" src="https://github.com/user-attachments/assets/ab53dbf4-d940-4297-a733-4eee19dc8c0e" />

<img width="150" height="450" alt="33171da07a944d62a4d2e6f65a181fa4" src="https://github.com/user-attachments/assets/3b79679a-0088-467c-bce4-792ae67c4d13" />




✨ Features

🧭 Smart Navigation

Tab-based layout for seamless category and product browsing.

Fragment-based structure for modular and scalable design.

🛍️ Product Management

View products by category.

Detailed product view including images, price, and description.

Integrated image carousel using ViewPager2.

📦 Dynamic Data Loading

Backend connected via Retrofit for REST API calls.

Product images and details fetched dynamically from server.

💾 Local Storage

SharedPreferences for saving user preferences.

SQLite / Room (optional) for managing cart or history data.

🌐 Image Loading

Uses Glide for smooth image loading and caching.

⚡ Optimized Performance

Lazy loading, RecyclerView adapters, and efficient view binding.

XML layouts optimized for responsiveness across devices.

🏗️ Project Structure
EcommerceApp/
│
├── app/
│   ├── java/com/example/ecommerceapp/
│   │   ├── activities/
│   │   │   ├── MainActivity.java
│   │   │   ├── DashboardActivity.java
│   │   │   └── ProductDetailsActivity.java
│   │   │
│   │   ├── adapters/
│   │   │   ├── CategoryAdapter.java
│   │   │   └── ProductAdapter.java
│   │   │
│   │   ├── models/
│   │   │   ├── Category.java
│   │   │   ├── Product.java
│   │   │   └── User.java
│   │   │
│   │   ├── network/
│   │   │   ├── ApiClient.java
│   │   │   └── ApiService.java
│   │   │
│   │   └── utils/
│   │       └── Constants.java
│   │
│   ├── res/
│   │   ├── layout/
│   │   │   ├── activity_main.xml
│   │   │   ├── activity_dashboard.xml
│   │   │   ├── activity_product_details.xml
│   │   │   └── item_product.xml
│   │   ├── drawable/
│   │   └── values/
│   └── AndroidManifest.xml
│
└── build.gradle

⚙️ Tech Stack
Layer	Technology
Frontend (UI)	XML, ViewPager2, RecyclerView, Material Design
Backend Communication	Retrofit2 (REST API)
Image Loading	Glide
Data Model	Java POJOs
Architecture	MVVM / Activity + Fragment based
Build Tool	Gradle
IDE	Android Studio
🔗 API Integration

The app communicates with your backend using RESTful APIs:

@GET("products.php")
Call<List<Product>> getProducts();

@GET("categories.php")
Call<List<Category>> getCategories();


API Base URL:

http://10.0.2.2/myshop/

🧩 Key Components
Component	Purpose
DashboardActivity	Displays product categories and navigation tabs.
ProductAdapter	Binds product data using View Binding and RecyclerView.
CategoryAdapter	Handles category list with view binding.
ProductDetailsActivity	Shows product details with ViewPager2 image slider.
ApiClient / ApiService	Handles Retrofit setup and API endpoints.
🚀 How to Run the Project

Clone the repository

git clone https://github.com/<your-username>/EcommerceApp.git


Open in Android Studio

Sync Gradle and install dependencies

Run your local backend (e.g., using XAMPP or WAMP):

Place API files in htdocs/myshop

Use the same base URL in your app

Run the app on Emulator or Physical Device

💡 Future Enhancements

Add authentication (Login / Signup)

Implement Add-to-Cart and Checkout features

Integrate Payment Gateway (e.g., Razorpay / Stripe)

Dark mode and animations for smoother UX

Firebase Push Notifications
