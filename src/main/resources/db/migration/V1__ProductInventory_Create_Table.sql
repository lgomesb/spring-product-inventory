create table Product_Inventory (
    id uuid not null,
    product_id uuid not null,
    quantity int not null default 0,
    created_by varchar(100) not null default '99999',
    created_on timestamp(6),
    modified_by varchar(100),
    modified_on timestamp(6),
    status varchar(1) not null default 'A',
    primary key (id)
);