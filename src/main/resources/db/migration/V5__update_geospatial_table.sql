ALTER TABLE geospatial
    ADD COLUMN related_task_ids jsonb NOT NULL DEFAULT '[]'::jsonb;

UPDATE geospatial
SET related_task_ids = CASE
    WHEN related_task_id IS NOT NULL THEN to_jsonb(ARRAY[related_task_id])
    ELSE '[]'::jsonb
END;

ALTER TABLE geospatial DROP COLUMN related_task_id;
