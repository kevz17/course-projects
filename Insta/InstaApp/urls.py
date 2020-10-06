from django.urls import path

from InstaApp.views import (EditProfile, ExploreView, PostCreateView,
                            PostDeleteView, PostDetailView, PostsView,
                            PostUpdateView, SignUp, UserProfile, addLike,
                            addComment, toggleFollow)

urlpatterns = [
    path('', PostsView.as_view(), name='home'),
    path('post/<int:pk>/', PostDetailView.as_view(), name='post'),
    path('post/new/', PostCreateView.as_view(), name='post_create'),
    path('post/update/<int:pk>/', PostUpdateView.as_view(), name='post_update'),
    path('post/delete/<int:pk>/', PostDeleteView.as_view(), name='post_delete'),
    path('auth/signup', SignUp.as_view(), name='signup'),
    path('user_profile/<int:pk>/', UserProfile.as_view(), name='profile'),
    path('edit_profile/<int:pk>/', EditProfile.as_view(), name='edit_profile'),
    path('togglefollow', toggleFollow, name='togglefollow'),
    path('like', addLike, name='addLike'),
    path('comment', addComment, name='addComment'),
    path('explore', ExploreView.as_view(), name='explore'),
]