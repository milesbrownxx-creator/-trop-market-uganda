-- TROP Market Uganda: visitor product reviews
-- Run this entire script in Supabase Dashboard > SQL Editor > New query.
-- Review visibility: everyone may read approved reviews; visitors can only submit pending reviews.

create table if not exists public.product_reviews (
  id uuid primary key default gen_random_uuid(),
  product_id text not null check (char_length(product_id) between 1 and 100),
  reviewer_name text not null check (char_length(trim(reviewer_name)) between 2 and 60),
  rating integer not null check (rating between 1 and 5),
  review_text text not null check (char_length(trim(review_text)) between 5 and 1000),
  status text not null default 'pending' check (status in ('pending','approved','rejected')),
  created_at timestamptz not null default now()
);

alter table public.product_reviews enable row level security;
grant usage on schema public to anon, authenticated;
grant select, insert on public.product_reviews to anon, authenticated;

-- Reset only these policies so the script can be safely run again.
drop policy if exists "Anyone can read approved TROP product reviews" on public.product_reviews;
drop policy if exists "Visitors can submit pending TROP product reviews" on public.product_reviews;

create policy "Anyone can read approved TROP product reviews"
  on public.product_reviews
  for select
  to anon, authenticated
  using (status = 'approved');

create policy "Visitors can submit pending TROP product reviews"
  on public.product_reviews
  for insert
  to anon, authenticated
  with check (status = 'pending');

-- No public UPDATE or DELETE policy is created. Approve/reject reviews as the project owner in the Supabase dashboard.
