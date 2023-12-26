#https://miniaimer.com/blog/facial-recognition-with-tensorflow-a-comprehensive-tutorial-on-training-models-with-python-code

import os
import cv2
import numpy as np

data_dir = 'img_align_celeba'
labels_file = 'list_attr_celeba.txt'

# Read the labels file and extract the names of the images and their labels
with open(os.path.join(data_dir, labels_file)) as f:
    lines = f.readlines()
    lines = lines[2:]  # Ignore the first two lines of the file
    filenames = []
    labels = []
    for line in lines:
        parts = line.strip().split()
        filename = parts[0]
        label = int(parts[21])
        filenames.append(filename)
        labels.append(label)

# Load the images and resize them to 224x224 pixels
images = []
for filename in filenames:
    img = cv2.imread(os.path.join(data_dir, filename))
    img = cv2.resize(img, (224, 224))
    images.append(img)

# Convert the images and labels to numpy arrays
images = np.array(images)
labels = np.array(labels)

import tensorflow as tf
from tensorflow.keras.applications import VGG16

# Load the VGG16 model
base_model = VGG16(weights='imagenet', include_top=False, input_shape=(224, 224, 3))

# Freeze the base model layers
for layer in base_model.layers:
    layer.trainable = False

from tensorflow.keras.layers import Flatten, Dense, Dropout

# Add layers on top of the base model
x = base_model.output
x = Flatten()(x)
x = Dense(256, activation='relu')(x)
x = Dropout(0.5)(x)
predictions = Dense(1, activation='sigmoid')(x)

# Create the final model
model = tf.keras.models.Model(inputs=base_model.input, outputs=predictions)

# Compile the model
model.compile(loss='binary_crossentropy',
              optimizer='adam',
              metrics=['accuracy'])

# Train the model
history = model.fit(images, labels, epochs=10, batch_size=32, validation_split=0.2)

import matplotlib.pyplot as plt

# Plot the training and validation accuracy
plt.plot(history.history['accuracy'])
plt.plot(history.history['val_accuracy'])
plt.title('Model accuracy')
plt.ylabel('Accuracy')
plt.xlabel('Epoch')
plt.legend(['Train', 'Validation'], loc='upper left')
plt.show()

# Plot the training and validation loss
plt.plot(history.history['loss'])
plt.plot(history.history['val_loss'])
plt.title('Model loss')
plt.ylabel('Loss')
plt.xlabel('Epoch')
plt.legend(['Train', 'Validation'], loc='upper left')
plt.show()

# Evaluate the model on test images
test_loss, test_acc = model.evaluate(test_images, test_labels)
print('Test accuracy:', test_acc)

# Make predictions on new images
predictions = model.predict(new_images)

# Print the predictions
for i in range(len(predictions)):
    if predictions[i] > 0.5:
        print('Image', i+1, 'contains the target face')
    else:
        print('Image', i+1, 'does not contain the target face')


# Save the trained model
model.save('facial_recognition_model.h5')

# from tensorflow.keras.models import load_model
#
# # Load the trained model
# model = load_model('facial_recognition_model.h5')
