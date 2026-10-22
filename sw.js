const CACHE_NAME = 'trop-market-shell-v12-network-first-pages';
const APP_SHELL = ['./', './index.html', './supabase-config.js', './affiliate-links.js', './manifest.webmanifest', './icons/icon-192.png', './icons/icon-512.png'];
self.addEventListener('install', (event) => {
  event.waitUntil(caches.open(CACHE_NAME).then((cache) => cache.addAll(APP_SHELL)).then(() => self.skipWaiting()));
});
self.addEventListener('activate', (event) => {
  event.waitUntil(caches.keys().then((keys) => Promise.all(keys.filter((key) => key !== CACHE_NAME).map((key) => caches.delete(key)))).then(() => self.clients.claim()));
});
self.addEventListener('fetch', (event) => {
  const request = event.request;
  const url = new URL(request.url);
  if (request.method !== 'GET' || url.origin !== self.location.origin) return;

  // Always try the network first for page navigations so visitors see the newest GitHub Pages version.
  const isPage = request.mode === 'navigate' || url.pathname.toLowerCase().endsWith('.html') || url.pathname.toLowerCase().endsWith('.htm');
  if (isPage) {
    event.respondWith(fetch(request, { cache: 'no-cache' }).then((response) => {
      if (response && response.ok) {
        const copy = response.clone();
        caches.open(CACHE_NAME).then((cache) => {
          cache.put(request, copy);
          cache.put(new URL('./index.html', self.location.href).href, response.clone());
        });
      }
      return response;
    }).catch(() => caches.match(request).then((cached) => cached || caches.match('./index.html'))));
    return;
  }

  // Static assets remain cache-first for faster visits, with online updates saved for next time.
  event.respondWith(caches.match(request).then((cached) => cached || fetch(request).then((response) => {
    if (response && response.ok) {
      const copy = response.clone();
      caches.open(CACHE_NAME).then((cache) => cache.put(request, copy));
    }
    return response;
  }).catch(() => caches.match('./index.html'))));
});
