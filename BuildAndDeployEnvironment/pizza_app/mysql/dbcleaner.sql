DELETE FROM order_line WHERE id>0;
DELETE FROM order_stage WHERE id>0;
DELETE FROM order_table WHERE id>0;
DELETE FROM customer WHERE id>0;

commit;
