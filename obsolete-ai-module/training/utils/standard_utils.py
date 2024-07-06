import io


def write_bytes_to_file(content: bytes, path: str):
    bytes_io = io.BytesIO(content)

    with open(path, 'wb') as file:
        file.write(bytes_io.getvalue())
