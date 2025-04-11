insert into order_status (id_order, status, creation_date) values
(1, 'CREATED', '2025-02-25T10:00:00')
on conflict do nothing;

insert into order_status (id_order, status, creation_date) values
(2, 'CREATED', '2025-03-17T10:00:00')
on conflict do nothing;

