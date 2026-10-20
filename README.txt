TROP MARKET UGANDA — PWA ICON FIX KIT
Developer: Agona Peter

Your PWABuilder screenshots show the two icon image URLs return 404 Not Found. The existing manifest already points to these exact names: icons/icon-192.png and icons/icon-512.png. This kit supplies real PNG images at the correct 192×192 and 512×512 dimensions. It also includes a sw.js with a bumped cache version so the browser re-checks and reinstalls the service worker after the icons are added.

IMPORTANT: These files have not been published to GitHub. The GitHub integration did not allow an automatic write, so use the steps below on your phone.

PART A — ADD THE ICONS
1. Download and extract this ZIP on your Samsung phone.
2. Open https://github.com/milesbrownxx-creator/-trop-market-uganda in Chrome and sign in.
3. At the repository root, choose Add file > Create new file.
4. For the file name enter icons/README.txt, type a short line such as “TROP Market icons folder”, and commit to main. This creates the icons folder.
5. Open the icons folder you just created. Choose Add file > Upload files.
6. Upload icon-192.png and icon-512.png from the extracted icons folder and commit both files.

PART B — UPDATE SERVICE WORKER
7. Return to the repository root and open sw.js. Tap the pencil/Edit button.
8. Replace the whole content with the supplied sw.js from this ZIP (open the extracted file in a text viewer, copy all, return to GitHub, select all existing code, paste).
9. Commit the change to main. This updates the cache version to v6 so the browser retries the service-worker installation.

DO NOT replace index.html or supabase-config.js. The current manifest already points to the icon paths supplied here, so normally you do not need to change manifest.webmanifest. A reference copy is included.

PART C — VERIFY
10. Wait 1–3 minutes for GitHub Pages to publish, then open these two addresses: 
https://milesbrownxx-creator.github.io/-trop-market-uganda/icons/icon-192.png
https://milesbrownxx-creator.github.io/-trop-market-uganda/icons/icon-512.png
Each should show an icon, not a 404 page.
11. Open PWABuilder and rerun the website check. If the offline service-worker issue remains, open the site in Chrome, refresh, and rerun the check after another minute.
