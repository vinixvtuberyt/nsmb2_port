import zipfile
import os

output_apk = "../output/nsmb2_port_final.apk"
if os.path.exists(output_apk):
    os.remove(output_apk)

with zipfile.ZipFile(output_apk, 'w', zipfile.ZIP_DEFLATED) as zipf:
    for root, dirs, files in os.walk('.'):
        for file in files:
            if file.endswith('.py') or file == 'make_apk.py':
                continue
            file_path = os.path.join(root, file)
            arcname = os.path.relpath(file_path, '.')
            zipf.write(file_path, arcname)
print("APK gerado com sucesso!")
