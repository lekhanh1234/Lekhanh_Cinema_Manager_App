Mo hinh 

Cinema_Manager_App/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── kotlin/
│   │   │   │   └── com/
│   │   │   │       └── cinemamanager/
│   │   │   │           ├── data/
│   │   │   │           │   ├── model/
│   │   │   │           │   │   ├── User.kt
│   │   │   │           │   │   ├── Movie.kt
│   │   │   │           │   │   ├── Category.kt
│   │   │   │           │   │   ├── FoodDrink.kt
│   │   │   │           │   │   └── Booking.kt
│   │   │   │           │   ├── repository/
│   │   │   │           │   │   ├── UserRepository.kt
│   │   │   │           │   │   ├── MovieRepository.kt
│   │   │   │           │   │   ├── CategoryRepository.kt
│   │   │   │           │   │   └── FoodDrinkRepository.kt
│   │   │   │           │   └── network/
│   │   │   │           │       ├── ApiService.kt
│   │   │   │           │       └── RetrofitClient.kt
│   │   │   │           ├── ui/
│   │   │   │           │   ├── activities/
│   │   │   │           │   │   ├── MainActivity.kt
│   │   │   │           │   │   ├── LoginActivity.kt
│   │   │   │           │   │   ├── RegisterActivity.kt
│   │   │   │           │   │   ├── UserProfileActivity.kt
│   │   │   │           │   │   ├── AdminDashboardActivity.kt
│   │   │   │           │   │   ├── MovieDetailsActivity.kt
│   │   │   │           │   │   └── BookingHistoryActivity.kt
│   │   │   │           │   ├── fragments/
│   │   │   │           │   │   ├── MovieListFragment.kt
│   │   │   │           │   │   ├── CategoryListFragment.kt
│   │   │   │           │   │   ├── FoodDrinkListFragment.kt
│   │   │   │           │   │   └── BookingFragment.kt
│   │   │   │           │   └── adapters/
│   │   │   │           │       ├── MovieAdapter.kt
│   │   │   │           │       ├── CategoryAdapter.kt
│   │   │   │           │       └── FoodDrinkAdapter.kt
│   │   │   │           ├── utils/
│   │   │   │           │   ├── Constants.kt
│   │   │   │           │   └── Utils.kt
│   │   │   │           └── viewmodels/
│   │   │   │               ├── UserViewModel.kt
│   │   │   │               ├── MovieViewModel.kt
│   │   │   │               ├── CategoryViewModel.kt
│   │   │   │               └── FoodDrinkViewModel.kt
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   │   ├── activity_main.xml
│   │   │   │   │   ├── activity_login.xml
│   │   │   │   │   ├── activity_register.xml
│   │   │   │   │   ├── activity_user_profile.xml
│   │   │   │   │   ├── activity_admin_dashboard.xml
│   │   │   │   │   ├── activity_movie_details.xml
│   │   │   │   │   └── activity_booking_history.xml
│   │   │   │   ├── drawable/
│   │   │   │   └── values/
│   │   │   │       ├── strings.xml
│   │   │   │       ├── colors.xml
│   │   │   │       └── styles.xml
│   │   │   └── AndroidManifest.xml
│   │   └── test/
│   │       └── kotlin/
│   │           └── com/
│   │               └── cinemamanager/
│   │                   ├── UserViewModelTest.kt
│   │                   ├── MovieRepositoryTest.kt
│   │                   └── CategoryRepositoryTest.kt
├── build.gradle
└── settings.gradle




Các tính năng chính của Cinema Manager App:
- Có module quản lý tài khoản theo 2 role riêng biệt là Admin (quản trị) và người dùng (user) bao gồm các tính năng: đăng nhập, đăng ký, quên mật khẩu, đổi mật khẩu, hiển thị user profile, đăng xuất.
- Admin có thể thêm, sửa, xóa, hiển thị danh sách thể loại phim (Category). Tìm kiếm Category theo keyword.
- Admin có thể thêm, sửa, xóa, hiển thị danh sách đồ ăn/đồ uống (Food/Drink). Tìm kiếm Food/Drink theo keyword.
- Admin có thể thêm, sửa, xóa, hiển thị danh sách các bộ phim (Movie). Tìm kiếm Movie theo keyword hoặc theo thể loại phim (Category).
- Admin có thể xem lịch sử đặt vé của tất cả người dùng (Booking History). Có thể tìm kiếm Booking History theo ID hoặc là scan QR Code.
- Admin có thể quản lý doanh thu của rạp phim được thống kê theo từng bộ phim tương ứng

- User có thể xem danh sách các bộ phim phổ biến nhất (nhiều người xem nhất), danh sách thể loại phim (Category), danh sách tất cả các bộ phim (Movie) chiếu trong rạp ngoài màn hình Home
- User có thể tìm kiếm phim theo keyword hoặc theo từng thể loại (Category)
- User có thể xem danh sách các bộ phim tưng ứng với từng thể loại (Category) riêng biệt
- User có thể xem chi tiết bộ phim: thông tin chi tiết, mô tả phim, video trailer
- User có thể đặt vé xem phim: chọn phòng chiếu phim, giờ chiếu phim, chọn số lượng vé muốn đặt, vị trí ghế trong rạp tương ứng, đồ ăn/đồ uống (Food/Drink) đi kèm.
- User có thể thực hiện thanh toán theo 2 phương thức: Tiền mặt hoặc Paypal
- User có thể xem lại lịch sử đặt vé của mình (Booking History), có tính năng tạo QR Code để giúp admin check vé khi tới rạp chiếu phim.
