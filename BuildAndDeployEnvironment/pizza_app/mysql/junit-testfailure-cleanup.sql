DELETE from gps_data WHERE driver_id > 4;

DELETE from driver WHERE id > 4;

DELETE from order_line WHERE productConfiguration_id IN (SELECT id from product_configuration WHERE product_id > 30);

DELETE from order_stage WHERE id IN (SELECT order_id from order_line WHERE productConfiguration_id IN (SELECT id from product_configuration WHERE product_id > 30));

DELETE from order_table WHERE id IN (SELECT order_id from order_line WHERE productConfiguration_id IN (SELECT id from product_configuration WHERE product_id > 30));

DELETE from product_configuration WHERE product_id > 30;

DELETE from product WHERE id > 30;

DELETE from customer WHERE company LIKE 'Test%' AND department LIKE 'TestDepartment%' AND level LIKE 'TestLevel%'