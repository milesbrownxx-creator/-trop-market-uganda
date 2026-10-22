TROP MARKET UGANDA — REVIEWS + GOOGLE SEARCH SETUP
Developer: Agona Peter
Contact: milesbrownxx@gmail.com

WHAT THIS PACKAGE ADDS
- Product cards now open a product details panel with the Jumia destination link and a review section.
- Visitors can choose 1–5 stars and send a review with their name and comment.
- Reviews are stored in Supabase (shared across visitors) once you configure the public publishable key and run supabase_reviews.sql.
- New reviews are pending approval so people cannot directly publish spam. Approved reviews show in product details and the catalog rating line.
- Includes search-friendly title/description, website metadata, robots.txt, and a sitemap template.

PART A — CONNECT SUPABASE REVIEWS (REQUIRED FOR SHARED REVIEWS)
1. Open https://supabase.com/dashboard/project/hmzoiftmlwsdnkkbvfsf
2. Go to Project Settings / API Keys (or Connect) and copy the PUBLIC PUBLISHABLE KEY. It starts with sb_publishable_. A legacy anon public key is also a public key.
3. Open supabase-config.js and replace PASTE_YOUR_SUPABASE_PUBLISHABLE_KEY_HERE with that public key. Keep the project URL already in the file.
4. Never use sb_secret_, service_role, a database password, or any secret key in the website.
5. In Supabase, open SQL Editor > New query. Open supabase_reviews.sql from this package, copy all its contents, paste into SQL Editor, and Run. This creates a separate product_reviews table and will not overwrite the existing products table.
6. After publishing the website, open a product, submit a test review, and check Table Editor > product_reviews. It should show status=pending.
7. To approve a review: Supabase > Table Editor > product_reviews > edit that row's status from pending to approved and save. Only approved reviews appear publicly. Reject spam by changing status to rejected.

PART B — MAKE IT EASIER FOR GOOGLE TO FIND THE SITE
Important: this package cannot guarantee that Google will index or rank a page. Google decides whether and when a page appears; this may take days or weeks.

1. First publish the updated site to GitHub Pages using the repository's root files and icons/ folder.
2. Open your live TROP Market website. Copy its full URL from Chrome's address bar.
3. Edit sitemap.xml: replace https://YOUR-GITHUB-USERNAME.github.io/YOUR-REPOSITORY/ with the exact live website URL, including the repository path and trailing slash where appropriate. Do not publish a sitemap with the placeholder still in it.
4. In robots.txt, uncomment the Sitemap line and replace the placeholder with your exact sitemap URL. Example format: Sitemap: https://YOUR-USERNAME.github.io/YOUR-REPO/sitemap.xml
5. Upload sitemap.xml and robots.txt to the same root folder as index.html in GitHub, then commit the changes.
6. In Chrome go to https://search.google.com/search-console/ and sign in with your Google account.
7. Choose Add property > URL prefix and enter the full live website URL. Complete one of the ownership verification methods Google offers for your site. Follow the instructions shown in Search Console.
8. After verification, open Sitemaps, enter sitemap.xml, and click Submit.
9. In URL Inspection, enter the live homepage URL, run the live test, and choose Request indexing if Google says the page can be indexed.
10. Check later by searching Google for the exact website URL or `site:YOUR-LIVE-HOST`. Results are not instant and requesting indexing does not guarantee inclusion.

SEO NOTES
- The homepage has a title, description, author, social-sharing metadata and WebSite structured data.
- This is a single-page catalogue linking to Jumia. Review stars in Google are not guaranteed. Do not add fake reviews or copy ratings from Jumia; only use reviews visitors submit on TROP Market.
- Search engines may take time to render interactive content. Publish with a real, accessible HTTPS URL and avoid noindex settings.
- TROP Market is an independent discovery catalogue, not an official Jumia website or partnership.

PUBLISHING ON GITHUB PAGES
Upload/replace index.html, supabase-config.js, sw.js, manifest.webmanifest, robots.txt, sitemap.xml, supabase_reviews.sql, SETUP_README.txt, and the icons/ folder. Keep all files in the repository root except the icon files in icons/.

LATEST WEBSITE UPDATE — 22 OCTOBER 2026
- Added current Jumia catalogue snapshots for Samsung Galaxy A06, Oraimo Traveler 15 power bank, Tankoo jug set, and Hoffmans blender.
- Product cards display indicative price snapshots and discount comparisons where a reference price was shown. Jumia prices and availability can change; visitors must confirm the live product page.
- Added product-specific sharing links, a shopping guide section, official Jumia Flash Sales and Official Stores shortcuts, and FAQs.
- This website remains an independent product-discovery catalogue. Checkout, payment, delivery, seller systems, shared customer listings and returns are not operated by TROP Market.
- Visitor reviews will be shared across devices only after the owner adds the Supabase PUBLIC publishable key and runs supabase_reviews.sql. Never use a secret/service-role key in the browser.


JUMIA AFFILIATE TRACKING SETUP — 22 OCTOBER 2026

TROP Market can now use a genuine approved affiliate tracking URL for each product.
A normal https://www.jumia.ug product link does not by itself prove a referral and does
not make TROP Market earn commission.

1. Start at the official Jumia Uganda JForce website: https://jforce.jumia.ug/
2. Sign up or sign in with your own Jumia account and review the programme's eligibility,
   commission rules, eligible order conditions, and payout method. Never pay an unofficial
   person who promises to activate commissions.
3. From the programme dashboard, obtain the real tracking link format that Jumia approves
   for your account. Ask JForce support whether your link can point to specific product pages.
4. Edit affiliate-links.js and add the exact approved URL for each product ID. Examples of
   product IDs are comments inside that file. Do not invent tracking query strings or place
   passwords, secret API keys or payment details in the source code.
5. After the site deploys, open TROP Market, refresh it, and tap a product. Products with
   a valid configured tracking URL show “TRACKED LINK” and an affiliate disclosure in the
   product-details panel. Products without one keep their normal Jumia link.

Commission tracking will not be active until you are accepted into the official programme
and the actual tracking URLs are configured and tested. A click is not a guaranteed commission;
Jumia sets eligibility and normally confirms commission based on qualifying completed/delivered
orders under its terms. This website cannot independently verify orders or promise earnings.

Official Jumia information:
- JForce Uganda: https://jforce.jumia.ug/
- Jumia Group business services: https://group.jumia.com/about/business-services
- Jumia Uganda: https://www.jumia.ug/


JFORCE DEFAULT AFFILIATE LINK — 22 OCTOBER 2026

The owner supplied this general JForce affiliate link:
https://jforce.jumia.ug/s/NimjUjJ

TROP Market now uses this as the default destination for product shopping buttons when a
product-specific affiliate URL has not been configured. This lets the visitor follow the
owner's JForce link, but the general link may not take the visitor directly to the exact
product they tapped. For the most relevant shopping experience, add product-specific links
only when JForce provides them. Earnings are not guaranteed and depend on Jumia's tracking,
eligibility rules and qualifying purchases.
