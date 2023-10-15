from Crypto.Cipher import ARC4
from math import log2
import string

filename = "" # type filepath

letter_frequency = {
    "a": 8.167,
    "b": 1.492,
    "c": 2.782,
    "d": 4.253,
    "e": 12.702,
    "f": 2.228,
    "g": 2.015,
    "h": 6.094,
    "i": 6.966,
    "j": 0.153,
    "k": 0.772,
    "l": 4.025,
    "m": 2.406,
    "n": 6.749,
    "o": 7.507,
    "p": 1.929,
    "q": 0.095,
    "r": 5.987,
    "s": 6.327,
    "t": 9.056,
    "u": 2.758,
    "v": 0.978,
    "w": 2.360,
    "x": 0.150,
    "y": 1.974,
    "z": 0.074
}


def read_from_file(filename):
    with open(filename, 'rb') as file:
        return file.read()


def keys():
    keys = []
    for i in range(26):
        for j in range(26):
            for k in range(26):
                key = chr(97 + i) + chr(97 + j) + chr(97 + k)
                keys.append(key)
    return keys


def entropy(bytes):
    ent = 0.0
    size = len(bytes)
    for i in range(256):
        prob = bytes.count(i) / size
        if prob > 0:
            ent += prob * log2(prob)

    return -ent


def decrypt(filename, keys):
    encrypteddata = read_from_file(filename)
    for key in keys:
        cipher = ARC4.new(key)
        decrypteddata = cipher.decrypt(encrypteddata)
        if entropy(decrypteddata) < 6:
            return decrypteddata


def ceasar_encrypt(text, shift):
    encrypted_text = ""
    temp = str(text)
    for char in temp:
        if char.isalpha():
            is_upper = char.isupper()
            alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" if is_upper else "abcdefghijklmnopqrstuvwxyz"
            shifted_char = alphabet[(alphabet.index(char) + shift) % 26]
            encrypted_text += shifted_char if is_upper else shifted_char.lower()
        else:
            encrypted_text += char
    return encrypted_text


def calculate_freq(text):
    text = text.lower()
    frequency = {}
    for char in text:
        if char.isalpha():
            if char in frequency:
                frequency[char] += 1
            else:
                frequency[char] = 1
    return frequency


def calculate_err(freq, english_freq, text):
    err = 0
    for char, count in english_freq.items():
        err += (freq[char] * 100 / len(text) - count) ** 2
    return err


output = decrypt(filename, keys())
lowest_err = 100000
lowest_err_shift = 0
for shift in range(1, 26):
    current_err = calculate_err(calculate_freq(ceasar_encrypt(output, shift)), letter_frequency, output )
    if current_err < lowest_err:
        lowest_err = current_err
        lowest_err_shift = shift

print(ceasar_encrypt(output, lowest_err_shift))

