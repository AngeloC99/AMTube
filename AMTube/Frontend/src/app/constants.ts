export const URL_BASE = 'http://localhost:9000/AMTube/';

export const URL = {
  LOGIN: URL_BASE + 'auth/login',
  REGISTRATION: URL_BASE + 'auth/registration',
  NOTIFICATIONS: URL_BASE + 'videoNotifications',
  SUBSCRIPTIONS: URL_BASE + 'subscriptions',
  VIDEOS: URL_BASE + 'videos',
  VIDEO: URL_BASE + 'videos/video',
  THUMBNAILS: URL_BASE + 'videos/thumbnails',
  VIDEOS_BY_USER_ID: URL_BASE+'videos/findByUserId',
  USERS: URL_BASE + 'users',
};

export const X_AUTH = 'X-Auth';

export const AUTH_TOKEN = 'auth-token';

export const USER_ID = 'user-id';