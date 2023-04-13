## Tests CI
```main:```
![badge](https://github.com/SergeevnaEkaterina/BlockChain/actions/workflows/mavenTests.yml/badge.svg?branch=main)
```develop:```
![badge](https://github.com/SergeevnaEkaterina/BlockChain/actions/workflows/mavenTests.yml/badge.svg?branch=develop1)

# BlockChain
Блокчейн -- это распределённая база данных, где каждый участник может хранить, просматривать, проверять но не удалять данные. Все данные разбиваются на блоки, и каждый блок имеет односвязную связность с предыдущими блоками, позволяющую верифицировать эти блоки. Верификацией занимаются как раз узлы, которые и поддерживают работоспособность блокчейна.

Каждый блок состоит из следующих полей:

index -- номер блока. Номера возрастают по порядку с первого.
prev_hash -- хеш предыдущего блока.
hash -- хеш текущего блока. Его нужно будет вычислить.
data -- данные... В нашем случае пусть это будет строка длинной случайных 256 символов.
nonce -- это дополнение, которое нужно будет сделать в блоке таким образом, чтобы выполнялось требование по хешированию.
Производство блока это простая операция. Нужно сконкатенировать поля index, prev_hash, data и nonce, и результат записать в поле hash. Но результат можно записывать только при условии, что hash заканчивается на 4 ноля. Если это не так, то нужно увеличить дополнение (nonce) и снова попробовать вычислить хеш (в качестве хеш-функции предлагаю использовать sha256, но окончательный выбор оставляю за вами).

Как только новый блок сгенерирован, то нужно отправить его остальным узлам (нодам) сети, и переходить к генерации следующего блока (т.е. index увеличивается, data нужно сгенерировать по новой, а в качестве prev_hash использовать хеш предыдущего блока). В то же время, пока мы подбираем хеш, кто-то из соседних узлов мог уже добиться успеха в этом, и присылает нам свой блок. Тогда нужно проверить, что хеш посчитан правильно, и переходить к генерации нового блока используя полученный в качестве предыдущего (если хеш не правильный, или блок произведён от уже устаревшего блока -- его нужно игнорировать).

Так же нужно следить, чтобы нода не оказалась в minority. Такое возможно, если она пропустила какой-то блок, и пытается генерировать новые блоки от уже устаревшего блока. В это время остальные ноды и блокчейн ушли уже вперёд. Чтобы разрешить эту ситуацию, можно попросить блоки у своих соседней, проверить их цепочки и использовать "новый" последний блок для продолжения работы по генерации блоков.
## Результат запуска

![image.png](image.png)


