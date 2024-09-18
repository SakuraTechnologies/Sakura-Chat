import re


def unindent_text(text, indent_size=2):
    """减少文本每一行的缩进量，每次减少两格"""
    unindented_lines = []
    for line in text.split('\n'):
        stripped_line = line.lstrip()
        original_indent = len(line) - len(stripped_line)
        if original_indent >= indent_size:
            new_indent = original_indent - indent_size
            unindented_line = ' ' * new_indent + stripped_line
        else:
            unindented_line = stripped_line
        unindented_lines.append(unindented_line)

    return '\n'.join(unindented_lines)


def add_public_static_doc_comments(lines):
    """在带有 public static 的代码上方添加文档注释"""
    processed_lines = []
    for i, line in enumerate(lines):
        if 'static' in line:
            # 在 public static 之前添加文档注释
            if i > 0 and not lines[i-1].startswith('/**'):
                params = extract_parameters(line)
                processed_lines.append(f'/**@param {params}*/')
        processed_lines.append(line)

    return processed_lines


def extract_parameters(code):
    """提取 public static 方法定义后面的括号内的参数"""
    pattern = r'open\s+fun\s+\w+\s+(\w+)\s*\(([^)]*)\)\s*{'
    match = re.search(pattern, code)
    if match:
        method_name = match.group(1)
        parameters = match.group(2)
        return method_name, parameters
    return None, None


def process_file(file_path):
    """读取文件内容，处理并写回文件"""
    with open(file_path, 'r', encoding='utf-8') as file:
        text = file.read()

    pattern = r'\{([\s\S]*?)\}'
    matches = re.findall(pattern, text)
    for match in matches:
        unindented_match = unindent_text(match, 4)
        text = text.replace('{' + match + '}', '{' + unindented_match + '}')

    # 添加文档注释
    lines = text.split('\n')

    lines = add_public_static_doc_comments(lines)
    processed_text = '\n'.join(lines)

    with open(file_path, 'w', encoding='utf-8') as file:
        file.write(processed_text)


def main():
    file_path = input("请输入文件路径: ")
    with open(file_path, 'r', encoding='utf-8') as file:
        code = file.read()

    method_name, parameters = extract_parameters(code)

    process_file(file_path)
    print(f"文件已处理并保存到 {file_path}")


if __name__ == "__main__":
    main()
