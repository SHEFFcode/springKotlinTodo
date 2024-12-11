INSERT INTO TASK (is_reminder_set, is_task_open, craeted_on, simple_id, id, description, priority) OVERRIDING USER VALUE VALUES (false, false, CURRENT_TIME, 1, '123e4567-e89b-12d3-a456-426655440000', 'hello', 'LOW')

INSERT INTO TASK (is_reminder_set, is_task_open, craeted_on, simple_id, id, description, priority) OVERRIDING USER VALUE VALUES (false, true, CURRENT_TIME, 2, '123e4567-e89b-12d3-a456-426655440001', 'goodbye', 'HIGH')

INSERT INTO TASK (is_reminder_set, is_task_open, craeted_on, simple_id, id, description, priority) OVERRIDING USER VALUE VALUES (false, false, CURRENT_TIME, 3, '123e4567-e89b-12d3-a456-426655440002', 'hio', 'MEDIUM')