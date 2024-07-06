sensitive_data_query: str = """
SELECT u.id as id,
       u.email as email,
       usd.sensitive_data_id as sensitive_data_id, 
       sd.product_id as product_id, 
       i.title as image_title,
       i.id as image_id
FROM users u
        INNER JOIN users_sensitive_data usd on u.id = usd.user_id
        INNER JOIN sensitive_data sd on sd.id = usd.sensitive_data_id
        INNER JOIN product p on sd.product_id = p.id
        INNER JOIN sensitive_data_images sdi on sd.id = sdi.sensitive_data_id
        INNER JOIN image i on sdi.images_id = i.id
"""
