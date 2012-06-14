-- löschen aller GPS-Daten von Testfahrern.
DELETE from gps_data 
 WHERE driver_id IN ( SELECT id from driver 
                       WHERE name LIKE 'TestDriver%'
                    );

-- löschen aller Testfahrer
DELETE from driver 
 WHERE name LIKE 'TestDriver%';

-- löschen aller Bestellposten, von Testkunden
DELETE from order_line 
 WHERE productConfiguration_id IN ( SELECT id from product_configuration 
                                    WHERE product_id IN (SELECT id from product 
                                                          WHERE description LIKE 'TestDescription%'
                                                            AND imageUrl LIKE 'testURL%'
                                                            AND name LIKE 'testName%'
                                                        )
                                  );

-- löschen aller Bestellphasen, von Testprodukten
DELETE from order_stage 
 WHERE id IN ( SELECT order_id 
                 from order_line 
                WHERE productConfiguration_id IN ( SELECT id from product_configuration 
                                                    WHERE product_id IN ( SELECT id from product 
                                                                          WHERE description LIKE 'TestDescription%'
                                                                            AND imageUrl LIKE 'testURL%'
                                                                            AND name LIKE 'testName%'
                                                                         )
                                                 )
             );

-- löschen aller Bestellungen, von Testprodukten.
DELETE from order_table 
 WHERE id IN ( SELECT order_id 
                 from order_line 
                WHERE productConfiguration_id IN ( SELECT id from product_configuration 
                                                    WHERE product_id IN ( SELECT id from product 
                                                                          WHERE description LIKE 'TestDescription%'
                                                                            AND imageUrl LIKE 'testURL%'
                                                                            AND name LIKE 'testName%'
                                                                         )
                                                 )
             );

-- löschen aller Produktekonfiguration, die nicht initial angelegt wurden.
DELETE from product_configuration 
 WHERE product_id IN (SELECT id from product 
                       WHERE description LIKE 'TestDescription%'
                         AND imageUrl LIKE 'testURL%'
                         AND name LIKE 'testName%'
                     );

-- löschen aller Produkte, die nicht initial angelegt wurden.
DELETE from product 
 WHERE description LIKE 'TestDescription%'
   AND imageUrl LIKE 'testURL%'
   AND name LIKE 'testName%';

-- löschen aller order stages von Testkunden
DELETE FROM order_stage WHERE order_id IN (
    SELECT id FROM order_table WHERE customer_id IN (
        SELECT id FROM customer 
         WHERE company LIKE 'Test%' 
           AND department LIKE 'TestDepartment%' 
           AND level LIKE 'TestLevel%'
    )
);

-- löschen aller bestellungen von Testkunden
DELETE from order_table 
 WHERE customer_id 
    IN ( SELECT id 
           from customer 
           WHERE company LIKE 'Test%' 
             AND department LIKE 'TestDepartment%' 
             AND level LIKE 'TestLevel%'
       );

-- löschen aller Testkunden
DELETE from customer 
       WHERE company LIKE 'Test%' 
         AND department LIKE 'TestDepartment%' 
        AND level LIKE 'TestLevel%';
